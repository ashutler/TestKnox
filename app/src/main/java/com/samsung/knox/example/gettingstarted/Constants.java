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

/**
 * Provide a valid Knox Platform for Enterprise (KPE) license as a constant. This technique is used for demonstration purposes
 * only.
 * Consider using more secure approach for passing your license key in a commercial scenario.
 * Visit https://docs.samsungknox.com/dev/common/knox-licenses.htm for details
 */

public interface Constants {
    /**
     * NOTE:
     * There are three types of license keys available:
     * - KPE Development Key
     * - KPE Commercial Key - Standard
     * - KPE Commercial Key - Premium
     *
     * The KPE Development and KPE Commercial Key - Standard can be generated from KPP while a KPE
     * Commercial Key can be bought from a reseller.
     *
     * You can read more about how to get a licenses at:
     * https://docs.samsungknox.com/dev/common/tutorial-get-a-license.htm
     *
     * Do not hardcode license keys in an actual commercial scenario
     * TODO Implement a secure mechanism to pass KPE key to your application
     *
     */

    // TODO Enter the KPE Development, KPE Standard license key or KPE Premium license key
    String KPE_LICENSE_KEY = "";

    // TODO: Enter a backwards-compatible key
    /**
     * Find more information about how to use the backward-compatibility key here:
     * https://docs.samsungknox.com/dev/knox-sdk/faqs/licensing/what-backwards-compatible-key.htm
     *
     * The button to activate the backwards compatibility key will only appear if the
     * device is between Knox version 2.5 and 2.7.1 / Knox API 17 to 21
     *
     * For more details see:
     * https://docs.samsungknox.com/dev/knox-sdk/faqs/licensing/what-backwards-compatible-key.htm
     */
    String BACKWARDS_COMPATIBLE_KEY = "";

}
