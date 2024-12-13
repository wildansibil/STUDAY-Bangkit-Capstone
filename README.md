<div align="center">
  <img src="https://raw.githubusercontent.com/wildansibil/STUDAY-Bangkit-Capstone/main/GitHub%20assets/studay.png" alt="Gambar" width="500"/>
</div>


# STUDAY : Capstone Project

This is the source code for our bangkit capstone project STUDAY.

## Team ID : C242-PS569

## Our Team

| Nama | Bangkit ID | Path |
|:---:|:---:|:---:|
| Wildan Sibil Danillah Safri | M180B4KY4485 | Machine Learning |
| Faberian Diantama | M180B4KY1306 | Machine Learning |
| Alamah Ulzanati Habibullah | M180B4KX0311 | Machine Learning |
| Wulan Zahra Putri | C134B4KX4509 | Cloud Computing |
| Kahlil Rifqi Samoedra Bangun | C528B4KY2129 | Cloud Computing |
| Muhammad Rizal Maulana | A284B4KY3053 | Mobile Development |


## What Is STUDAY?

For our bangkit capstone project, we made an app called Studay (Stuuday Day). Studay is and app to identifying a common challenge in early childhood education, the need for engaging tools to help children learn calistung (reading, writing, and arithmetic). With this application, we hope that we can reduce reducing the number of illiterates and those who cannot count in Indonesia.

## Tech Stack 

<div align="center">
  <img src="https://raw.githubusercontent.com/wildansibil/STUDAY-Bangkit-Capstone/main/GitHub%20assets/tech%20stack.png" alt="Gambar" width="500"/>
</div>

## Key Technologies

- TensorFlow for machine learning model development.
- TensorFlow Lite (TFLite) for deploying the model on Android devices.
- Firebase Authentication for secure login and user management.
- Firebase Realtime Database for real-time data synchronization.
- Google Cloud for hosting the ML model and API interactions.

## Project Architecture 

## Application Workflow

Login and Authentication:
- Users log into the app via Firebase Authentication.
- Upon successful login, the app can fetch user-specific data (e.g., progress, past performance) from the Realtime Database.
  
ML Prediction:
- The app uses the TensorFlow Lite model (deployed on Google Cloud) for prediction tasks, such as personalized learning recommendations, performance analysis, or     behavior prediction.
- The app loads the TFLite model and sends the data (e.g., student’s learning history) to generate predictions or analysis.
  
Data Storage and Sync
- Data (e.g., quizzes, assignments, user performance) is stored in Firebase Realtime Database, where it can be accessed and updated in real-time. This allows the 
  app to provide instant feedback and track the student’s progress over time.

## Documentation Link

### [Machine Learning](https://github.com/wildansibil/STUDAY-Bangkit-Capstone/tree/main/Machine%20Learning)
### [Mobile Development](https://github.com/muzallana/studay)
### [Cloud Computing](https://github.com/wildansibil/STUDAY-Bangkit-Capstone/tree/main/Backend)




