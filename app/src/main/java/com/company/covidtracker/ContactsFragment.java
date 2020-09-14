package com.company.covidtracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.covidtracker.Model.ContactModel.ContactRoot;
import com.company.covidtracker.Model.ContactModel.Primary;
import com.company.covidtracker.Model.ContactModel.Regional;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ContactsFragment extends Fragment {

    private final String CONTACT_API = "https://api.rootnet.in/covid19-in/contacts";
    private ImageView phoneNumber, tollFreeNumber, emailAddress, facebook, twitter, media;
    private TableLayout regionalContactsTable;
    private RecyclerView recyclerView;
    private ContactsAdapter adapter;
    private final int CALL_PHONE = 1888;
    private String phNumber, tfnumber, emailAddr, facebookAddr, twitterAddr, mediaLink;
    private LinearLayout mainLayout;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phoneNumber = view.findViewById(R.id.contact_primary_number);
        tollFreeNumber = view.findViewById(R.id.contact_primary_toll_free);
        emailAddress = view.findViewById(R.id.contact_primary_email);
        facebook = view.findViewById(R.id.contact_primary_fb);
        twitter = view.findViewById(R.id.contact_primary_twitter);
        media = view.findViewById(R.id.contact_primary_media);

        progressBar = view.findViewById(R.id.contact_progress_bar);
        mainLayout = view.findViewById(R.id.contacts_layout_main);

        recyclerView = view.findViewById(R.id.contact_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

//        regionalContactsTable = view.findViewById(R.id.contacts_regional);

        phNumber = "";
        tfnumber = "";
        emailAddr = "";
        facebookAddr = "";
        twitterAddr = "";
        mediaLink = "";

        getContactDetails();

        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phNumber.isEmpty()) Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_SHORT).show();
                else makePhoneCall(phNumber);
            }
        });

        tollFreeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tfnumber.isEmpty()) Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_SHORT).show();
                else makePhoneCall(tfnumber);
            }
        });

        emailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailAddr.isEmpty()) Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_SHORT).show();
                else sendEmail(emailAddr);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if  (facebookAddr.isEmpty()) Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_SHORT).show();
                else openBrowserURL(facebookAddr);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (twitterAddr.isEmpty()) Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_SHORT).show();
                else openBrowserURL(twitterAddr);
            }
        });

        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaLink.isEmpty()) Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_SHORT).show();
                else openBrowserURL(mediaLink);
            }
        });
    }

    private void getContactDetails() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(CONTACT_API)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Some failure...");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    Gson gson = new Gson();
                    String responseString = response.body().string();
                    final ContactRoot contactRoot = gson.fromJson(responseString, ContactRoot.class);
                    JSONObject jsonObject = new JSONObject(responseString);
                    String tollNumber = jsonObject.getJSONObject("data").getJSONObject("contacts").getJSONObject("primary").get("number-tollfree").toString();
                    contactRoot.getData().getContacts().getPrimary().setNumber_tollfree(tollNumber);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                mainLayout.setVisibility(View.VISIBLE);
                                setPrimaryContacts(contactRoot.getData().getContacts().getPrimary());
//                            setRegionalContacts(contactRoot.getData().getContacts().getRegional());
                                setUpRecyclerview(contactRoot.getData().getContacts().getRegional());
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setPrimaryContacts(Primary primaryContacts) {
        phNumber = primaryContacts.getNumber();
        tfnumber = primaryContacts.getNumber_tollfree();
        emailAddr = primaryContacts.getEmail();
        facebookAddr = primaryContacts.getFacebook();
        twitterAddr = primaryContacts.getTwitter();
        mediaLink = primaryContacts.getMedia().get(0);
//        phoneNumber.setText(primaryContacts.getNumber());
//        tollFreeNumber.setText(primaryContacts.getNumber_tollfree());
//        emailAddress.setText(primaryContacts.getEmail());
//        facebookAddr.setText(primaryContacts.getFacebook());
//        twitterAddr.setText(primaryContacts.getTwitter());
//        mediaLink.setText(primaryContacts.getMedia().get(0));
    }

    private void setUpRecyclerview(List<Regional> contacts) {
        adapter = new ContactsAdapter(contacts, getContext());
        recyclerView.setAdapter(adapter);
    }

    private void setRegionalContacts(List<Regional> regionalContacts) {
        destroyTableChildViews(regionalContactsTable);
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        TableLayout.LayoutParams headerParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerParams.setMargins(0, 0, 0, 20);
        TableRow header = new TableRow(getContext());
        header.setLayoutParams(headerParams);
        header.setWeightSum(2);
        TextView state = getEmptyRegionalTextView(params);
        state.setText("State");
        state.setTextSize(20);
        header.addView(state);
        TextView numberHead = getEmptyRegionalTextView(params);
        numberHead.setText("Number");
        numberHead.setTextSize(20);
        header.addView(numberHead);
        regionalContactsTable.addView(header, 0);

        for (int i = 0; i < regionalContacts.size(); i++) {
            Regional curr = regionalContacts.get(i);
            TableRow row = new TableRow(getContext());
            row.setWeightSum(2);
            TextView stateName = getEmptyRegionalTextView(params);
            stateName.setText(curr.getLoc());
            row.addView(stateName);
            TextView number = getEmptyRegionalTextView(params);
            number.setText(curr.getNumber());
            row.addView(number);
            regionalContactsTable.addView(row, i+1);
        }
    }

    private TextView getEmptyRegionalTextView(TableRow.LayoutParams params) {
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(params);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return tv;
    }

    private void destroyTableChildViews(TableLayout layout) {
        int childCount = layout.getChildCount();

        if (childCount > 1) {
            layout.removeViews(1, childCount - 1);
        }
    }

    private void makePhoneCall(String number) {
        if (checkPermission(Manifest.permission.CALL_PHONE)) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            getContext().startActivity(intent);
        } else {
            requestPermission(Manifest.permission.CALL_PHONE, CALL_PHONE);
        }
    }

    private void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        startActivity(Intent.createChooser(emailIntent, "Choose email provider..."));
    }

    private void openBrowserURL(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        getContext().startActivity(intent);
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{permission},
                requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALL_PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permission granted");

                } else {
                    Toast.makeText(getContext(), "Call permission is required", Toast.LENGTH_LONG).show();
                }
        }
    }
}
