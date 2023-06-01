package com.example.busreservationapp;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.FirebaseFirestore;

        import java.text.NumberFormat;
        import java.util.Calendar;
        import java.util.Locale;

public class PaymentDetailActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    private FirebaseAuth auth;

    private Trip trip;

    private String totalPrice, tripId, busId, bookedSeat, arrivalDate, userId;

    public static final String EXTRA_TRIP_ID = "extra_trip_id";

    public static final String EXTRA_BUS_ID = "extra_bus_id";

    public static final String EXTRA_BOOKED_SEAT = "extra_booked_seat";

    private Locale localeID = new Locale("in", "ID");

    private TextView tvUserName, tvUserPhoneNumber, tvUserBusName, tvUserDepartureTime, tvDepartureDate,
            tvArrivalDate, tvArrivaltime, tvDepartureCity, tvDepartureTerminal, tvArrivalTerminal, tvArrivalCity,
            tvUserEstimatedTime, tvUserTicket, tvUserSeats, tvUserPriceTotal;

    private int selectedYear, selectedMonth, selectedDay;

    private String priceTotal;

    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        tripId  = getIntent().getStringExtra(EXTRA_TRIP_ID);
        busId = getIntent().getStringExtra(EXTRA_BUS_ID);
        bookedSeat = getIntent().getStringExtra(EXTRA_BOOKED_SEAT);

        if (tripId == null || busId == null || bookedSeat == null) {
            Toast.makeText(this, "Failed to get data", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            getTrip(tripId);
        }
        tvUserName = findViewById(R.id.tvUserName);
        tvUserPhoneNumber = findViewById(R.id.tvUserPhoneNumber);
        tvUserBusName = findViewById(R.id.tvUserBusName);
        tvUserDepartureTime = findViewById(R.id.tvUserDepartureTime);
        tvDepartureDate = findViewById(R.id.tvDepartureDate);
        tvArrivalDate = findViewById(R.id.tvArrivalDate);
        tvArrivaltime = findViewById(R.id.tvArrivalTime);
        tvDepartureCity = findViewById(R.id.tvDepartureCity);
        tvArrivalTerminal = findViewById(R.id.tvArrivalTerminal);
        tvDepartureTerminal = findViewById(R.id.tvDepartureTerminal);
        tvArrivalCity = findViewById(R.id.tvArrivalCity);
        tvUserEstimatedTime = findViewById(R.id.tvUserEstimatedTime);
        tvUserTicket = findViewById(R.id.tvUserTicket);
        tvUserSeats = findViewById(R.id.tvUserSeats);
        tvUserPriceTotal = findViewById(R.id.tvUserPriceTotal);

        Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);

        userId = auth.getCurrentUser().getUid();


        if (tripId==null) {
            Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (busId==null) {
            Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (bookedSeat==null) {
            Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
            finish();
        }

        getUserData(userId);
        getTrip(tripId);

    }

    private void getUserData(String userId) {
        db.collection("users").document(userId)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        User user = doc.toObject(User.class);
                        tvUserName.setText(user.getName());
                        tvUserPhoneNumber.setText(user.getPhoneNumber());
                    }
                });
    }

    private void getTrip(String tripId) {
        db.collection("trip").document(tripId)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        trip = doc.toObject(Trip.class);
                        setTrip(trip);
                    }
                });


    }

    private void setTrip(Trip trip){
        tvUserBusName.setText(trip.getBusName());
        tvUserDepartureTime.setText(trip.getTimeDeparture());
        String dateTrip = selectedDay + " " + (selectedMonth + 1) + " " + selectedYear;
        tvDepartureDate.setText(dateTrip);
        tvArrivaltime.setText(trip.getTimeArrival());
        tvDepartureCity.setText(trip.getAsal());
        tvDepartureTerminal.setText(trip.getDepartureTerminal());
        tvArrivalCity.setText(trip.getTujuan());
        tvArrivalTerminal.setText(trip.getArrivalTerminal());
        tvUserEstimatedTime.setText(trip.getWaktu());
        String seatsTotal = "1";
        int price = Integer.parseInt(trip.getHarga());
        int seats = Integer.parseInt(seatsTotal);
        double total = price * seats;
        priceTotal = String.valueOf(total);
        tvUserTicket.setText(seatsTotal +" x Rp"+ trip.getHarga());
        tvUserSeats.setText(seatsTotal);
        tvUserPriceTotal.setText(formatRupiah.format((double)total));


    }
}
