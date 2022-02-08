/**
 * DISCLAIMER: PLEASE TAKE NOTE THAT THE SAMPLE APPLICATION AND
 * SOURCE CODE DESCRIBED HEREIN IS PROVIDED FOR TESTING PURPOSES ONLY.
 * <p>
 * Samsung expressly disclaims any and all warranties of any kind,
 * whether express or implied, including but not limited to the implied warranties and conditions
 * of merchantability, fitness for com.samsung.knox.example.genericvpnpolicy particular purpose and
 * non-infringement.
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

import android.util.Log;
import android.widget.TextView;

public class Utils {

    private TextView mTextView;
    private String mTag;

    public Utils(TextView view, String className) {
        mTextView = view;
        mTag = className;
    }

    /** Log results to a mTextView in application UI */
    public void log(String text) {
        mTextView.append(text);
        mTextView.append("\n\n");
        mTextView.invalidate();
        Log.d(mTag,text);
    }

    /** Process the exception */
    public void processException(Exception ex, String TAG) {
        if (ex != null) {
            // present the exception message
            String msg = ex.getClass().getCanonicalName() + ": " + ex.getMessage();
            mTextView.append(msg);
            mTextView.append("\n\n");
            mTextView.invalidate();
            Log.e(TAG, msg);
        }
    }
}
