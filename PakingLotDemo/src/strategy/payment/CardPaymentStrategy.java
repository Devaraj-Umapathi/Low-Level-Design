package strategy.payment;

import model.ParkingTicket;

public class CardPaymentStrategy implements PaymentStrategy{
    @Override
    public boolean pay(double fee, ParkingTicket ticket) {
        System.out.println("Paid ₹" + fee + " for ticket " + ticket.getTicketId() + " via Card.");
        return true;
    }
}
