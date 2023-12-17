# OurCulture
About
--
OurCulture app will help users to learn more about Balinese traditions, customs, and art forms. OurCulture is designed not only for educational image classification, but also as a dynamic marketplace where users can upload and sell authentic Balinese products. OurCulture app is equipped with a machine learning model that can detect cultural objects in Bali. Simply by take a picture with your phone, the machine learning model will automatically detect the object, and then the application will display complete information related to the detected object. The implementation of this machine learning model will certainly make it easier for users to learn various aspects of culture in Bali.

Android APK File (Minimum SDK API 24, Nougat Android 7.0)
--
Link: [https://drive.google.com/file/d/1cCxbGFdiKl4KDPeaePmiaQ9vThgjhAy_/view?usp=drive_link](https://drive.google.com/file/d/1cCxbGFdiKl4KDPeaePmiaQ9vThgjhAy_/view?usp=drive_link)

Mock-Up
--
Link: [https://www.figma.com/file/TNbFFpwhpEWyCQdHewoz78/figma-team?type=design&node-id=16-7&mode=design&t=k3B9VBdXWgkL3rk0-0](https://www.figma.com/file/TNbFFpwhpEWyCQdHewoz78/figma-team?type=design&node-id=16-7&mode=design&t=k3B9VBdXWgkL3rk0-0)

REST API Documentation
--
Link: [https://docs.google.com/document/d/1ca8_cRljpvR3bVRDADA0CR1qgrOWwjSroHNBPF6CQqk/edit#heading=h.3mprpr3agq37](https://docs.google.com/document/d/1ca8_cRljpvR3bVRDADA0CR1qgrOWwjSroHNBPF6CQqk/edit#heading=h.3mprpr3agq37)

App Features
--
![1](https://github.com/OurCultureBangkit/Mobile/assets/111882401/9a1fe538-aa50-4042-982d-7058a9b21f60)
![2](https://github.com/OurCultureBangkit/Mobile/assets/111882401/c8972cfd-b57f-442d-b23a-d829fe50564f)
![3](https://github.com/OurCultureBangkit/Mobile/assets/111882401/fa87c30b-b42d-425b-ae5a-2fd5464284b8)
![4](https://github.com/OurCultureBangkit/Mobile/assets/111882401/d2baa528-e06d-4443-b33d-1c68a150943e)
![5](https://github.com/OurCultureBangkit/Mobile/assets/111882401/df4be748-6d84-443f-8969-920e327ce86a)

Technology
--
- Android Studio IDE to develop the Android application.

Android Studio Project Installation
--
Components
---
OurCulture Android app is developed using Android Studio IDE. Here are components that we used:
- Developed using Kotlin language.
- Using paging 3 and its adapter to show item listing.
- Camera X: CameraX is used to capture image and take a picture from gallery.
- Retrofit: Simplify and facilitate communication with API (Application Programming Interface) efficiently.
- Glide: Used to load and display images from various sources, including URLs, URIs, local files, and Drawable resources.
- LiveData: Able to maintain data when configuration changes occur.
- View Model: to help the UI controller prepare the data to be displayed to the UI.

Requirements
---
- Android Studio Giraffe 2022.3.1
- Minimun Android SDK 24

Workflow
---
1. Clone The Project and Open It in Android Studio
   
git clone https://github.com/OurCultureBangkit/Mobile.git

2. Run or Build The App
After you open the project, wait for the Gradle to finish building first. Then you can choose to build debug app by using Run: Run'app'. Or you can build signed App by head to Build: Generate Signed Bundle/APK.
