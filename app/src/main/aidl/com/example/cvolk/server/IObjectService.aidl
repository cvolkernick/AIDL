// IObjectService.aidl
package com.example.cvolk.server;

import java.util.List;
import com.example.cvolk.server.ParcelableObject;

interface IObjectService {
    List<ParcelableObject> getObjects();
}