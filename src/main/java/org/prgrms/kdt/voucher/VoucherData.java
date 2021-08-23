package org.prgrms.kdt.voucher;

/**
 * Created by yhh1056
 * Date: 2021/08/23 Time: 6:53 오후
 */
public class VoucherData {

    private final String voucherNumber;
    private final long discount;

    public VoucherData(String inputVoucher) {
        isContainsSeparator(inputVoucher);

        String[] split = inputVoucher.split(",");
        String voucherNumber = split[0].trim();
        long discount = isNumeric(split[1].trim());
        isValidVoucherInput(voucherNumber, discount);

        this.voucherNumber = voucherNumber;
        this.discount = discount;
    }

    private void isContainsSeparator(String inputVoucher) {
        if (!inputVoucher.contains(",")) {
            throw new IllegalArgumentException("쉼표로 구분해서 입력해주세요.");
        }
    }

    public long isNumeric(String numberString) {
        long number;
        try {
            number = Long.parseLong(numberString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자 형식으로 입력해주세요");
        }
        return number;
    }

    private void isValidVoucherInput(String voucherNumber, long discount) {
        isEmpty(voucherNumber);
        isValidRange(discount);
        isValidPercent(voucherNumber, discount);
    }

    private void isEmpty(String voucherNumber) {
        if (voucherNumber.isEmpty()) {
            throw new IllegalArgumentException("바우처를 선택해주세요.");
        }
    }

    private void isValidRange(long percent) {
        if (percent < 0) {
            throw new IllegalArgumentException("할인금액 또는 할인율은 0보다 작을 수 없습니다.");
        }
    }

    private void isValidPercent(String voucherNumber, long percent) {
        if (voucherNumber.equals("2")) {
            if (percent < 0 || percent > 100) {
                throw new IllegalArgumentException("할인율은 100 이하로 입력해주세요.");
            }
        }
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public long getDiscount() {
        return discount;
    }
}
