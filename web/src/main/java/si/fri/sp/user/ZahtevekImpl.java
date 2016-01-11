package si.fri.sp.user;

import java.util.Date;

/**
 * @Author Gasper Andrejc, created on 11/jan/2016
 */
public class ZahtevekImpl {
    private String location;
    private String cost;
    private String dateFrom;
    private String dateTo;
    private String content;

    public ZahtevekImpl() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
