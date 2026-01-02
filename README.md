🛒 VPR Fresh | Quick Commerce Grocery App

A native Android application simulating a 10-minute grocery delivery ecosystem.

Built for the 5th Semester Mobile Programming Project.

📱 About

VPR Fresh is a hyper-local delivery application inspired by Blinkit and Zepto. It simulates a complete e-commerce flow, from secure onboarding to browsing products via a split-view category interface, managing a persistent shopping cart, and simulating a checkout process.

This project goes beyond basic UI/UX to implement robust engineering practices like Local Data Persistence and Asynchronous Image Loading.

✨ Key Features

🔐 Secure Authentication: Integrated Firebase Authentication with Google Sign-In for one-tap onboarding.

💾 Persistent Cart: Solved the "data loss" issue using SharedPreferences + Gson serialization. The cart state is saved locally and survives app restarts.

⚡ Split-View Categories: Engineered a synchronized Sidebar + Grid RecyclerView layout for seamless product filtering.

🔍 Real-Time Search: Instant product filtering using TextWatcher logic.

🖼️ Dynamic UI: Infinite scrolling banners using ViewPager2 and optimized image caching with Glide.

📸 App Screenshots

Onboarding & Auth

Login Screen

<img src="screenshots/SplashScreen.jpg" width="300">

<img src="screenshots/Sign_in.jpg" width="300">

Registration

Home Dashboard

<img src="screenshots/Sign_up.jpg" width="300">

<img src="screenshots/home.jpg" width="300">

Category Split-View

Persistent Cart

<img src="screenshots/category.jpg" width="300">

<img src="screenshots/cart.jpg" width="300">

User Profile

Order Success

<img src="screenshots/profile.jpg" width="300">

<img src="screenshots/orderSuccess.jpg" width="300">

🛠️ Tech Stack

Language: Kotlin

UI: XML, Material Design Components

Backend: Firebase Authentication

Local Storage: SharedPreferences, Gson

Libraries:

Glide (Image Loading)

ViewPager2 (Banners)

Google Play Services (Location & Auth)

🚀 How to Run

Clone the repository:

git clone [https://github.com/Vinodhacker17/VPR-Fresh.git](https://github.com/Vinodhacker17/VPR-Fresh.git)


Open the project in Android Studio.

Sync Gradle files.

Add your own google-services.json file from Firebase (if cloning fresh).

Run on an Emulator or Physical Device.

👥 Team

Vinod N - Lead Developer & Architecture

Rudra Pratap Singh - UI/UX & Testing

Prince Kumar Singh - Backend Integration

Mentor: Prof. Varshitha K

## © Copyright
**VPR Fresh** is an original project developed by **Vinod N**, **Rudra prathap Singh**, and **Prince Kumar Singh**.

This project is for educational and portfolio purposes. Unauthorized copying, modification, or distribution of this project as your own work is strictly prohibited.

Created for the **5th Semester Mobile Programming** course.
