package com.example.hari.mirrormirroronthewall;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

/**
 * Created by hari on 8/21/16.
 */
public interface LambdaCall {

    @LambdaFunction
    String TestFunction(DuhInfo info);
}
