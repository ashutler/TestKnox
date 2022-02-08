/**
 * DISCLAIMER: PLEASE TAKE NOTE THAT THE SAMPLE APPLICATION AND
 * SOURCE CODE DESCRIBED HEREIN IS PROVIDED FOR TESTING PURPOSES ONLY.
 * <p>
 * Samsung expressly disclaims any and all warranties of any kind,
 * whether express or implied, including but not limited to the implied warranties and conditions
 * of merchantability, fitness for com.samsung.knoxsdksample particular purpose and non-infringement.
 * Further, Samsung does not represent or warrant that any portion of the sample application and
 * source code is free of inaccuracies, errors, bugs or interruptions, or is reliable,
 * accurate, complete, or otherwise valid. The sample application and source code is provided
 * "as is" and "as available", without any warranty of any kind from Samsung.
 * <p>
 * Your use of the sample application and source code is at its own discretion and risk,
 * and licensee will be solely responsible for any damage that results from the use of the sample
 * application and source code including, but not limited to, any damage to your computer system or
 * platform. For the purpose of clarity, the sample code is licensed “as is” and
 * licenses bears the risk of using it.
 * <p>
 * Samsung shall not be liable for any direct, indirect or consequential damages or
 * costs of any type arising out of any action taken by you or others related to the sample application
 * and source code.
 */
package com.samsung.knox.example.gettingstarted;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.SystemManager;
import com.samsung.android.knox.keystore.TimaKeystore;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;

/**
 * This activity displays the main UI of the application. This is a simple application to restrict
 * use of the device camera using the Samsung Knox SDK.
 * Read more about the Knox SDK here:
 * https://docs.samsungknox.com/dev/knox-sdk/index.htm
 *
 * Please insert a valid Knox Platform for Enterprise (KPE) license key to {@link Constants}.
 *
 * @author Samsung R&D Canada Technical Publications
 */

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static final int DEVICE_ADMIN_ADD_RESULT_ENABLE = 1;

    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mDeviceAdmin;
    private Utils mUtils;
    private Button mToggleAdminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //...called when the activity is starting. This is where most initialization should go.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView logView = findViewById(R.id.logview_id);
        logView.setMovementMethod(new ScrollingMovementMethod());
        mUtils = new Utils(logView, TAG);

        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDeviceAdmin = new ComponentName(MainActivity.this, SampleAdminReceiver.class);

        mToggleAdminBtn = findViewById(R.id.toggleAdminBtn);
        mToggleAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {toggleAdmin();
            }
        });

        Button activateLicenseBtn = findViewById(R.id.activateLicenseBtn);
        activateLicenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateLicense();
            }
        });

        Button deactivateLicenseBtn = findViewById(R.id.deactivateLicenseBtn);
        deactivateLicenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deactivateLicense();
            }
        });

        Button activateBackwardsCompatibleKeyBtn = findViewById(R.id.activateBackwardsCompatibleKeyBtn);
        activateBackwardsCompatibleKeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateBackwardsCompatibleKey();
            }
        });

        // Backwards Compatibility button is only visible if the device is on at least
        // knox version 2.5 AND lower than knox version 2.8 (Knox API 17 to 21)
        if(EnterpriseDeviceManager.getAPILevel() < EnterpriseDeviceManager.KNOX_VERSION_CODES.KNOX_2_8 &&
                EnterpriseDeviceManager.getAPILevel() >= EnterpriseDeviceManager.KNOX_VERSION_CODES.KNOX_2_5) {
            activateBackwardsCompatibleKeyBtn.setVisibility(View.VISIBLE);
        }

        Button toggleCameraBtn = findViewById(R.id.toggleCameraBtn);
        toggleCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                toggleCameraState();
            }
        });
        Button toggleWidgetBtn = findViewById(R.id.addWidget);
        toggleWidgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addWidget();
            }
        });

        Button toggleTimaKeystoreStateBtn = findViewById(R.id.toggleTimaKeystoreState);
        toggleTimaKeystoreStateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTimaKeystoreState();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshButtons();
    }

    /** Handle result of device administrator activation request */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DEVICE_ADMIN_ADD_RESULT_ENABLE) {
            switch (resultCode) {
                // End user cancels the request
                case Activity.RESULT_CANCELED:
                    mUtils.log(getString(R.string.admin_cancelled));
                    break;
                // End user accepts the request
                case Activity.RESULT_OK:
                    mUtils.log(getString(R.string.admin_activated));
                    refreshButtons();
                    break;
            }
        }
    }

    /** Update button state */
    private void refreshButtons() {
        boolean adminActive = mDevicePolicyManager.isAdminActive(mDeviceAdmin);

        if (!adminActive) {
            mToggleAdminBtn.setText(getString(R.string.activate_admin));

        } else {
            mToggleAdminBtn.setText(getString(R.string.deactivate_admin));
        }
    }

    /** Present a dialog to activate device administrator for this application */
    private void toggleAdmin() {
        boolean adminActive = mDevicePolicyManager.isAdminActive(mDeviceAdmin);

        if (adminActive) {
            mUtils.log(getString(R.string.deactivating_admin));
            // Deactivate application as device administrator
            mDevicePolicyManager.removeActiveAdmin(new ComponentName(this, SampleAdminReceiver.class));
            mToggleAdminBtn.setText(getString(R.string.activate_admin));

        } else {
            mUtils.log(getString(R.string.activating_admin));
            // Ask the user to add a new device administrator to the system
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdmin);
            // Start the add device administrator activity
            startActivityForResult(intent, DEVICE_ADMIN_ADD_RESULT_ENABLE);
        }
    }

    /**
     * Note that embedding your license key in code is unsafe and is done here for demonstration
     * purposes only. Please visit https://docs.samsungknox.com/dev/common/knox-licenses.htm for more
     * details about license keys.
     */
    private void activateLicense() {
        // Instantiate the KnoxEnterpriseLicenseManager class to use the activateLicense method
        KnoxEnterpriseLicenseManager licenseManager = KnoxEnterpriseLicenseManager.getInstance(this);
        // License Activation TODO Add license key to Constants.java
        licenseManager.activateLicense(Constants.KPE_LICENSE_KEY);
        mUtils.log(getResources().getString(R.string.license_progress));
    }

    // call backwards-compatible key activation
    private void activateBackwardsCompatibleKey() {
        // Get an instance of the License Manager
        EnterpriseLicenseManager backwardsCompatibleKeyManager = EnterpriseLicenseManager.getInstance(this);
        // Activate the backwards-compatible license key
        backwardsCompatibleKeyManager.activateLicense(Constants.BACKWARDS_COMPATIBLE_KEY);
        mUtils.log(getResources().getString(R.string.backwards_compatible_key_activation));
    }

    /**
     *  Deactivate license key.
     */
    private void deactivateLicense() {
        // Instantiate the KnoxEnterpriseLicenseManager class to use the deactivateLicense method
        KnoxEnterpriseLicenseManager licenseManager = KnoxEnterpriseLicenseManager.getInstance(this);
        // License deactivation
        licenseManager.deActivateLicense(Constants.KPE_LICENSE_KEY);
        mUtils.log(getResources().getString(R.string.deactivate_license_progress));
    }

    /** Toggle the restriction of the device camera on/off. When set to disabled, the end user will
     * be unable to use the device cameras.
     */
    private void toggleCameraState() {
        // Instantiate the EnterpriseDeviceManager class
        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(this);
        // Get the RestrictionPolicy class where the setCameraState method lives
        RestrictionPolicy restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();

        boolean isCameraEnabled = restrictionPolicy.isCameraEnabled(false);

        try {
            // Toggle the camera state on/off
            boolean result = restrictionPolicy.setCameraState(!isCameraEnabled);
            if(result) {
                mUtils.log(getResources().getString(R.string.camera_state, !isCameraEnabled));
            } else {
                mUtils.log(getResources().getString(R.string.camera_fail));
            }

        } catch (SecurityException e) {
            mUtils.processException(e, TAG);
        }
    }
    private void addWidget() {
        try {
            CustomDeviceManager cdm = CustomDeviceManager.getInstance();
            SystemManager systemManager = cdm.getSystemManager();

            int result = systemManager.addWidget(0, 0, 0, 4, 1,
                "com.sec.android.app.clockpackage/com.sec.android.widgetapp.dualclockdigital.DualClockDigitalWidgetProvider");

            Log.d(TAG, "Added widget with return value: " + String.valueOf(result));
        } catch (RuntimeException e) {
            mUtils.processException(e, TAG);
        }
    }
    /**
     * Report the status (enabled/disabled) of the TIMA Keystore.
     * Requires Premium permissions.
     */
    private void toggleTimaKeystoreState() {
        EnterpriseKnoxManager enterpriseKnoxManager = EnterpriseKnoxManager.getInstance(this);
        TimaKeystore timaKeystore = enterpriseKnoxManager.getTimaKeystorePolicy();

        try {
            boolean isTimaKeystoreEnabled = timaKeystore.isTimaKeystoreEnabled();
            boolean result = timaKeystore.enableTimaKeystore(!isTimaKeystoreEnabled);

            if(result) {
                mUtils.log(getResources().getString(R.string.tima_keystore_state, !isTimaKeystoreEnabled));
            } else {
                mUtils.log(getResources().getString(R.string.tima_keystore_fail));
            }

        } catch (SecurityException e) {
            mUtils.processException(e, TAG);
        }
    }
}