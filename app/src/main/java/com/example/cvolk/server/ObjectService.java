package com.example.cvolk.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class ObjectService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private final IObjectService.Stub binder = new IObjectService.Stub() {

        @Override
        public List<ParcelableObject> getObjects() throws RemoteException {

            try {
                return MainActivity.db.getObjects(ParcelableObject.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new ArrayList<>();
        }
    };
}
