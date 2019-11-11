package ch.heigvd.amt.project.utils;

public class Pagination {

    private int amount;
    private int page;

    public Pagination(int amount, int page) {
        this.amount = checkValue(amount, 10);
        this.page = checkValue(page, 1);
    }

    public Pagination(String amount, String page, int defaultAmount, int defaultPage) {
        this.amount = checkValue(parseInt(amount, defaultAmount), defaultAmount);
        this.page = checkValue(parseInt(page, defaultPage), defaultPage);
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private int checkValue(int value, int defaultValue) {
        return value >= 1 ? value : defaultValue;
    }

    public int getOffset() {
        return amount * (page - 1);
    }

    public int getAmount() {
        return amount;
    }

    public int getPage() {
        return page;
    }
}
