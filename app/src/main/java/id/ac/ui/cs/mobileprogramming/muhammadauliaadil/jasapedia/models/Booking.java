package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "bookings", foreignKeys = @ForeignKey(entity = Service.class,
        parentColumns = "id", childColumns = "service_id",
        onUpdate = CASCADE, onDelete = CASCADE))
public class Booking {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "service_id", index = true)
    private int serviceId;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    public Booking(int serviceId, String note, String date, String time) {
        this.serviceId = serviceId;
        this.note = note;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
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
}
