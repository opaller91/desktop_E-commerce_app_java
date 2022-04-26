package ku.cs.shop.models.vouchers;

import java.util.ArrayList;

public class VoucherList {

    private ArrayList<Voucher> vouchers;

    public VoucherList() {
        vouchers = new ArrayList<>();
    }

    public void addVoucher(Voucher voucher) {
        vouchers.add(voucher);
    }

    public void removeVoucher(String codename) {
        Voucher voucher = findVoucher(codename);
        vouchers.remove(voucher);
    }

    public void removeVoucher(Voucher voucher) {
        vouchers.remove(voucher);
    }

    public ArrayList<Voucher> getVouchers() {
        return vouchers;
    }

    public Voucher findVoucher(String codename) {
        Voucher voucher = null;
        for (Voucher temp : vouchers) {
            if (codename.equals(temp.getCodename())) {
                voucher = temp;
                break;
            }
        }
        return voucher;
    }

    public String toCsv() {
        String result = "";
        for (Voucher voucher : vouchers) {
            result += voucher.toCsv() + "\n";
        }
        return result;
    }

    public void filterExpiredVoucher() {
        ArrayList<Voucher> tempList = new ArrayList<>();
        for (Voucher v : vouchers) {
            if (v.checkExpiryTime()) {
                tempList.add(v);
            }
        }
        vouchers = tempList;
    }
}
