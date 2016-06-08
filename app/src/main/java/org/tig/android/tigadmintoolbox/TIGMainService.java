package org.tig.android.tigadmintoolbox;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nguye on 6/8/2016.
 */
public class TIGMainService extends AbstractService {
    @Override
    public void onStartService() {
        Log.i("TIGMainService","Service started");
        send(Message.obtain(null, 1, 0, 0));
        Log.i("TIGMainService","Service send a message");
    }

    @Override
    public void onStopService() {

    }

    @Override
    public void onReceiveMessage(Message msg) {
        switch (msg.what){
            case 1:
                send(Message.obtain(null,1));
                Toast.makeText(TIGMainService.this,"received 1 from UI",Toast.LENGTH_SHORT).show();
        }
    }
}
