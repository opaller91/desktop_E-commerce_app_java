package ku.cs.shop.models;

import ku.cs.shop.models.reports.Report;
import ku.cs.shop.models.vouchers.Voucher;

import ku.cs.shop.models.vouchers.Voucher;

public class Information {
    private User user;
    private Shop shop;
    private Item item;
    private Voucher voucher;
    private Review review;
    private Report report;

    public Information(User user) {
        this.user = user;
        this.shop = null;
        this.item = null;
        this.review = null;
        this.voucher = null;
        this.report = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }


}
