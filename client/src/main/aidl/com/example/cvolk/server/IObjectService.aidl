// IObjectService.aidl
package com.example.cvolk.server;

import java.util.List;
import com.example.cvolk.client.ParcelableObject;

interface IObjectService {
    List<ParcelableObject> getObjects();
}