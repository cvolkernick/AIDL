package com.example.cvolk.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.cvolk.server.IObjectService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected IObjectService objectService = null;
    private RecyclerView.Adapter adapter;
    private List<ParcelableObject> objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        startService();
    }

    private void bindViews() {
        RecyclerView recyclerView = findViewById(R.id.rvObjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        objects = new ArrayList<>();
        adapter = new RecyclerAdapter(objects);
        recyclerView.setAdapter(adapter);
    }

    private void startService() {
        ComponentName aidlComponent = new ComponentName("com.example.cvolk.server", "com.example.cvolk.server.ObjectService");
        Intent it = new Intent();
        it.setComponent(aidlComponent);
        bindService(it, connection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            objectService = IObjectService.Stub.asInterface(service);
            try {
                Log.d("CLIENT_TAG",objectService.getObjects().size()+"");
                objects.clear();
                objects.addAll(objectService.getObjects());
                adapter.notifyDataSetChanged();
            } catch (RemoteException e) {
                //nothing
            }
            Toast.makeText(getApplicationContext(),	"Service Connected", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            objectService = null;
            Toast.makeText(getApplicationContext(), "Service Disconnected", Toast.LENGTH_SHORT).show();
        }
    };
}
