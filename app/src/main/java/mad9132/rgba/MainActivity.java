package mad9132.rgba;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Observable;
import java.util.Observer;

import model.HSVModel;

/**
 * The Controller for HSVModel.
 *
 * As the Controller:
 *   a) event handler for the View
 *   b) observer of the Model (HSVModel)
 *
 * Features the Update / React Strategy.
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 * @version 1.0
 *
 * RGBA model repurposed for HSV by Daniel Cormier
 *
 *
 */
public class MainActivity extends AppCompatActivity implements Observer
                                                            , SeekBar.OnSeekBarChangeListener
{

    // INSTANCE VARIABLES
    // Variables to track text boxes:
    private TextView            mColorSwatch, hueBox, satBox, valBox;
    private HSVModel            mModel;
    private SeekBar             mHueSB, mSatSB, mValSB;
    private Button              mBlackB, mRedB, mLimeB, mBlueB, mYellowB, mCyanB, mMagentaB,
                                mSilverB, mGrayB, mMaroonB, mOliveB, mGreenB, mPurpleB, mTealB,
                                mNavyB;
    private String  hueTitle,satTitle,valTitle;
    boolean trackedSat=false;
    boolean trackedVal=false;
    boolean trackedHue=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Instantiate a new HSV model
        // Initialize the model with black.
        mModel = new HSVModel(0,0,0);
        // The Model is observing this Controller (class MainActivity implements Observer)
        mModel.addObserver( this );

        // reference each View
        mColorSwatch = (TextView) findViewById( R.id.colorSwatch );
        hueBox=(TextView)findViewById(R.id.hue);
        satBox=(TextView)findViewById(R.id.sat);
        valBox=(TextView)findViewById(R.id.val);

        //Get initial titles for later manipulation.
        hueTitle=(String)hueBox.getText();
        satTitle=(String)satBox.getText();
        valTitle=(String)valBox.getText();

        //Reference seekbars.
        mHueSB = (SeekBar) findViewById( R.id.hueSB);
        mSatSB = (SeekBar) findViewById( R.id.satSB);
        mValSB = (SeekBar) findViewById( R.id.valSB);

        mHueSB.setOnSeekBarChangeListener( this );
        mSatSB.setOnSeekBarChangeListener( this );
        mValSB.setOnSeekBarChangeListener( this );

        //Reference buttons.
        mBlackB = (Button) findViewById(R.id.blackB);
        mRedB = (Button) findViewById(R.id.redB);
        mLimeB = (Button) findViewById(R.id.limeB);
        mBlueB = (Button) findViewById(R.id.blueB);
        mYellowB = (Button) findViewById(R.id.yellowB);
        mCyanB = (Button) findViewById(R.id.cyanB);
        mMagentaB = (Button) findViewById(R.id.magentaB);
        mSilverB = (Button) findViewById(R.id.silverB);
        mGrayB = (Button) findViewById(R.id.grayB);
        mMaroonB = (Button) findViewById(R.id.maroonB);
        mOliveB = (Button) findViewById(R.id.oliveB);
        mGreenB = (Button) findViewById(R.id.greenB);
        mPurpleB = (Button) findViewById(R.id.purpleB);
        mTealB = (Button) findViewById(R.id.tealB);
        mNavyB = (Button) findViewById(R.id.navyB);


        //Establish click listeners for same.  Might have been a trifle more efficient
        //to directly add those to the lines above, but legibility suffers.
        mBlackB.setOnClickListener(colorClicked);
        mRedB.setOnClickListener(colorClicked);
        mLimeB.setOnClickListener(colorClicked);
        mBlueB.setOnClickListener(colorClicked);
        mYellowB.setOnClickListener(colorClicked);
        mCyanB.setOnClickListener(colorClicked);
        mMagentaB.setOnClickListener(colorClicked);
        mSilverB.setOnClickListener(colorClicked);
        mGrayB.setOnClickListener(colorClicked);
        mMaroonB.setOnClickListener(colorClicked);
        mOliveB.setOnClickListener(colorClicked);
        mGreenB.setOnClickListener(colorClicked);
        mPurpleB.setOnClickListener(colorClicked);
        mTealB.setOnClickListener(colorClicked);
        mNavyB.setOnClickListener(colorClicked);

        // initialize the View to the values of the Model
        this.updateView();
    }

    //Custom click handler to deal with the palette buttons.
    protected View.OnClickListener colorClicked = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.blackB:
                    mModel.setBlack();
                    break;
                case R.id.redB:
                    mModel.setRed();
                    break;
                case R.id.limeB:
                    mModel.setLime();
                    break;
                case R.id.blueB:
                    mModel.setBlue();
                    break;
                case R.id.yellowB:
                    mModel.setYellow();
                    break;
                case R.id.cyanB:
                    mModel.setCyan();
                    break;
                case R.id.magentaB:
                    mModel.setMagenta();
                    break;
                case R.id.silverB:
                    mModel.setSilver();
                    break;
                case R.id.grayB:
                    mModel.setGray();
                    break;
                case R.id.maroonB:
                    mModel.setMaroon();
                    break;
                case R.id.oliveB:
                    mModel.setOlive();
                    break;
                case R.id.greenB:
                    mModel.setGreen();
                    break;
                case R.id.purpleB:
                    mModel.setPurple();
                    break;
                case R.id.tealB:
                    mModel.setTeal();
                    break;
                case R.id.navyB:
                    mModel.setNavy();
                    break;
            }
        }
    };

    /**
     * Event handler for the <SeekBar>s: hue, saturation, luminesence (value)
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        // Did the user cause this event?
        // YES > continue
        // NO  > leave this method
        if ( fromUser == false ) {
            return;
        }

        // Determine which <SeekBark> caused the event (switch + case)
        // GET the SeekBar's progress, and SET the model to it's new value
        switch ( seekBar.getId() ) {
            case R.id.hueSB:
                mModel.setHue( mHueSB.getProgress() );
                break;

            case R.id.satSB:
                mModel.setSat( mSatSB.getProgress() );
                break;

            case R.id.valSB:
                mModel.setVal( mValSB.getProgress() );
                break;

        }
    }

    //If the seekbars are being dragged, flag the correct one for realtime title updates.
    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {
        switch ( seekBar.getId() )
        {
            case R.id.hueSB:
                trackedHue=true;
                break;

            case R.id.satSB:
                trackedSat=true;
                break;

            case R.id.valSB:
                trackedVal=true;
                break;
        }

    }

    //The seekbars are no longer being dragged, reset the title and tracking.
    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
        switch ( seekBar.getId() )
        {
            case R.id.hueSB:
                hueBox.setText(hueTitle);
                trackedHue=false;
                break;

            case R.id.satSB:
                satBox.setText(satTitle);
                trackedSat=false;
                break;

            case R.id.valSB:
                valBox.setText(valTitle);
                trackedVal=false;
                break;
        }
    }

    // The Model has changed state!
    // Refresh the View to display the current values of the Model.
    @Override
    public void update(Observable observable, Object data) {
        this.updateView();
    }


    private void updateColorSwatch()
    {
        mColorSwatch.setBackgroundColor(Color.HSVToColor(mModel.getHSV()));
    }

    private void updateValSB()
    {
        int val=mModel.getValBar();
        mValSB.setProgress(val);
        if (trackedVal){valBox.setText(valTitle+" "+val+"%");}//Realtime title changes.

    }

    private void updateSatSB()
    {
        int sat=mModel.getSatBar();
        mSatSB.setProgress(sat);
        if (trackedSat){satBox.setText(satTitle+" "+sat+"%");}//as above.
    }

    private void updateHueSB()
    {
        int hue=mModel.getHueBar();
        mHueSB.setProgress(hue);
        if (trackedHue){hueBox.setText(hueTitle+" "+hue+"°");}// "
    }

    // synchronize each View component with the Model
    public void updateView() {
        this.updateColorSwatch();
        this.updateHueSB();
        this.updateValSB();
        this.updateSatSB();
    }
}
