package com.ecdms.ecdms.service.IMPL;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.PaymentFilterDTO;
import com.ecdms.ecdms.dto.request.PaymentReceiptDTO;
import com.ecdms.ecdms.dto.response.PaymentDTO;
import com.ecdms.ecdms.entity.Payment;
import com.ecdms.ecdms.entity.Student;
import com.ecdms.ecdms.exceptions.InternalServerErrorException;
import com.ecdms.ecdms.repository.PaymentRepository;
import com.ecdms.ecdms.repository.StudentRepository;
import com.ecdms.ecdms.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceIMPL implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final JavaMailSender mailSender;
    private final StudentRepository studentRepository;

    public void createYearlyPaymentPlan(Integer userId, String type, double amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1); // Set to first day of the month

        for (int i = 0; i < 12; i++) {
            Payment payment = new Payment();
            payment.setUserId(userId);
            payment.setType(type);
            payment.setAmount(amount);
            payment.setDueDate(cal.getTime());
            payment.setPaid(false);

            paymentRepository.save(payment);

            cal.add(Calendar.MONTH, 1);
        }
    }

    @Override
    public ResponseEntity getPayments(PaymentFilterDTO paymentFilterDTO) {
        try {
            List<Payment> paymentList = paymentRepository.findByUserID(paymentFilterDTO.getStudentID());
            List<PaymentDTO> paymentDTOList = new ArrayList<>();
            for (Payment payment:paymentList){
                Boolean isPendingApprove = (!payment.isPaid() && payment.getPaymentReceipt() != null) ? true : null;
                PaymentDTO paymentDTO = new PaymentDTO(
                        payment.getPaymentId(),
                        payment.getType(),
                        payment.getAmount(),
                        payment.getDueDate(),
                        payment.getPaidDate(),
                        isPendingApprove,
                        payment.isPaid(),
                        payment.getPaymentReceipt()
                );
                paymentDTOList.add(paymentDTO);
            }
            return new ResponseEntity(new StandardResponse(200,"All Payments",paymentDTOList), HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in get payments.");
        }
    }

    @Override
    public ResponseEntity makePayment(PaymentDTO paymentDTO) {
        try {
            Optional<Payment> byId = paymentRepository.findById(paymentDTO.getPaymentId());
            byId.get().setPaid(true);
            byId.get().setPaidDate(new Date());
            paymentRepository.save(byId.get());
            return new ResponseEntity(new StandardResponse(true,"Payment Successful Added."),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in make payment.");
        }
    }

    @Override
    public ResponseEntity submitPayment(PaymentReceiptDTO paymentReceiptDTO) {
        try {
            Optional<Payment> paymentOpt = paymentRepository.findById(paymentReceiptDTO.getPaymentId());
            if (paymentOpt.isPresent()) {
                Payment payment = paymentOpt.get();
                payment.setPaymentReceipt(paymentReceiptDTO.getReceiptUrl());
                payment.setPaidDate(new Date());
                paymentRepository.save(payment);
                return new ResponseEntity(new StandardResponse(true, "Payment receipt submitted successfully."), HttpStatus.OK);
            } else {
                return new ResponseEntity(new StandardResponse(false, "Payment not found."), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in submit payment.");
        }
    }

    @Override
    public ResponseEntity rejectPayment(PaymentDTO paymentDTO) {
        try {
            Optional<Payment> byId = paymentRepository.findById(paymentDTO.getPaymentId());
            if (byId.isPresent()){
                byId.get().setPaymentReceipt(null);
                paymentRepository.save(byId.get());
            }
            SimpleMailMessage message = new SimpleMailMessage();
            String userEmail = getUserEmail(byId.get().getUserId()); // Replace with real lookup

            message.setTo(userEmail);
            message.setSubject("Payment Rejected.");
            message.setText("Dear Parent,\n\n" +
                    "This is a reminder that you paid payment of Rs." + byId.get().getAmount() +
                    " for " + byId.get().getType() + " on " + formatDate(byId.get().getDueDate()) +"is rejected."+
                    ".\n\nPlease make sure upload the more clear receipt or contact us.\n\nBest regards,\nMontessori Admin");

            mailSender.send(message);
            return new ResponseEntity(new StandardResponse(true,"Payment Rejected."),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("Error occurred in reject payment.");
        }
    }

    @Scheduled(cron = "0 0 20 * * *")
    @Transactional
    public void sendMonthlyPaymentReminders() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrowDate = tomorrow.getTime();

        Calendar today = Calendar.getInstance();
        Date todayDate = today.getTime();

        List<Payment> payments = paymentRepository.findByDueDateBetween(todayDate, tomorrowDate);

        for (Payment payment : payments) {
            if (!payment.isPaid()) {
                boolean hasPaidNextMonth = checkIfPaidForNextMonth(payment.getUserId(), payment.getDueDate());

                if (!hasPaidNextMonth) {
                    sendEmailReminder(payment);
                }
            }
        }
    }

    private boolean checkIfPaidForNextMonth(Integer userId, Date currentDueDate) {
        Calendar nextMonth = Calendar.getInstance();
        nextMonth.setTime(currentDueDate);
        nextMonth.add(Calendar.MONTH, 1);

        Date nextDueDate = nextMonth.getTime();

        return paymentRepository.existsByUserIdAndDueDateAndPaidTrue(userId, nextDueDate);
    }

    private void sendEmailReminder(Payment payment) {
        SimpleMailMessage message = new SimpleMailMessage();
        String userEmail = getUserEmail(payment.getUserId()); // Replace with real lookup

        message.setTo(userEmail);
        message.setSubject("Upcoming Payment Reminder");
        message.setText("Dear Parent,\n\n" +
                "This is a reminder that your payment of ₹" + payment.getAmount() +
                " for " + payment.getType() + " is due on " + formatDate(payment.getDueDate()) +
                ".\n\nPlease make sure to complete the payment on time.\n\nBest regards,\nMontessori Admin");

        mailSender.send(message);
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("dd MMM yyyy").format(date);
    }

    private String getUserEmail(Integer userId) {
        // TODO: Fetch actual user email from UserRepository
        Optional<Student> byId = studentRepository.findById(userId);
        return byId.get().getEmail();
    }

}
