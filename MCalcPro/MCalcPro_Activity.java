package ca.yorku.eecs.mcalcpro;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ca.roumani.i2c.MPro;

public class MCalcPro_Activity extends AppCompatActivity implements TextToSpeech.OnInitListener, SensorEventListener
{
    private TextToSpeech tts;
    private Sensor mSensor;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.tts = new TextToSpeech(this, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mcalcpro_layout);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void buttonClicked(View v)
    {
        try
        {
            EditText pView = (EditText) findViewById(R.id.pBox);
            String price = pView.getText().toString();
            EditText aView = (EditText) findViewById(R.id.aBox);
            String amortization = aView.getText().toString();
            EditText iView = (EditText) findViewById(R.id.iBox);
            String interest = iView.getText().toString();

            MPro mp = new MPro();
            mp.setAmortization(amortization);
            mp.setInterest(interest);
            mp.setPrinciple(price);

            String str ="Monthly Payments = "+ mp.computePayment("%.2f") + "\n\nBy making this payment monthly for " + amortization + "years the mortgage will be paid in full." + "But if you terminate the mortgage on it's nth anniversary," + "the balance still owing depends on n as shown below:\n\n\n\t\t\t " + "n" + "\t" + "balance\n\n";

            int count = 0;
            while (count <= 20)
            {
                str += String.format("%8d", count) + mp.outstandingAfter(count, "%,16.0f") + "\n\n";
                if (count < 5)
                {
                    count += 1;
                } else
                {
                    count += 5;
                }
            }
            ((TextView) findViewById(R.id.output)).setText(str);
            tts.speak("Monthly Payments = "+ mp.computePayment("%.2f"), TextToSpeech.QUEUE_FLUSH, null);

        } catch (Exception e)
        {
            Toast label = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            label.show();
        }


    }

    @Override
    public void onInit(int initStatus)
    {
        this.tts.setLanguage(Locale.US);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        double ax = event.values[0];
        double ay = event.values[1];
        double az = event.values[2];
        double a = Math.sqrt(ax * ax + ay * ay + az * az);
        if (a > 20)
        {
            ((EditText) findViewById(R.id.pBox)).setText("");
            ((EditText) findViewById(R.id.aBox)).setText("");
            ((EditText) findViewById(R.id.iBox)).setText("");
            ((TextView) findViewById(R.id.output)).setText("");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}