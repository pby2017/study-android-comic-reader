# [following firebase comic reader app edu streaming by EDMT Dev](https://www.youtube.com/channel/UCllewj2bGdqB8U9Ld15INAg)

## study curriculum
1. [Setup Firebase and Splash Screen](https://youtu.be/7C5jl2CyzBw)
 * Create Firebase
 * Create Splash Screen (first screen for a seconds)
2. 
 * 


## record

# 2019 / 02 / 16 Sat
> setting

    [gradle - app]
    implementation 'com.android.support:design:28.0.0'
    implementation 'com.github.chrisbanes:PhotoView:2.1.0'
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation 'com.github.d-max:spots-dialog:1.1@aar'
    implementation 'com.ss.bannerslider:bannerslider:2.0.0'
    implementation 'com.wajahatkarim3.easyflipviewpager:easyflipviewpager:1.0.0'

    [gradle - project]
    maven {url 'https://jitpack.io'}

    [firebase connect]
    connect to firebase (need google login)
    add the realtime database to your app

    [gradle - app]
    implementation 'com.google.firebase:firebase-database:16.0.1'
    implementation 'com.google.firebase:firebase-core:16.0.1'

    [plugin]
    Install Android Drawable Importer
    Restart IDE

> splash screen

    [res]
    Batch Drawable Importer
    add style drawable image for splash_screen

    [SplashScreen.java]
    new Handler()

    [AndroidManifest.xml]
    change filter MAIN from MainActivity to SplashScreen
    add SplashScreen theme splash_screen in style
