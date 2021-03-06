package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookings")
public class Booking {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "service_name")
    private String serviceName;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "booking_fee")
    private int bookingFee;

    public Booking(String serviceName, String note, String date, String time, int bookingFee) {
        this.serviceName = serviceName;
        this.note = note;
        this.date = date;
        this.time = time;
        this.bookingFee = bookingFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBookingFee() {
        return bookingFee;
    }

    public void setBookingFee(int bookingFee) {
        this.bookingFee = bookingFee;
    }
}
