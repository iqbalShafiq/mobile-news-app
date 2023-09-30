# mobile-news-app
<img src="https://i.ibb.co/ZXCLWJP/Cover.png" alt="Cover" style="width:720px;"/>
## Table of Contents

- [Introduction](#introduction)
- [User Stories](#user-stories)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage And Screenshots](#usage-and-screenshots)
- [APK Download](#apk-download)

## Introduction

The News App is a mobile native application that allows users to access and browse news articles from various categories and sources using the [NewsAPI.org](https://newsapi.org) API. 
This application provides a user-friendly interface to explore news conveniently.

## User Stories

1. **Display News Categories**: Create a screen to display the list of news categories which are business, entertainment, general, health, science, sports, and technology.

2. **Show News Sources**: Show news sources when the user clicks on one of the news categories.

3. **Show News Articles**: Show news articles when the user clicks on one of the news sources.

4. **Article Detail**: Show the article detail in a web view when the user clicks on one of the articles.

5. **Search Functionality**: Provide a search function to search news sources and news articles.

6. **Endless Scrolling**: Implement endless scrolling on the news sources and articles screens for seamless browsing.

## Technologies Used

- [Native Android with Kotlin](https://developer.android.com/kotlin?hl=id) - For building native android application.
- [NewsAPI.org](https://newsapi.org) - API for fetching sources and articles of news data.
- [Retrofit2](https://square.github.io/retrofit/) - HTTP client for making API requests.
- [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=id) - Android library for implementing endless scrolling.
- [Koin](https://insert-koin.io/) - Dependency Injection framework.
- [Glide](https://github.com/bumptech/glide) - Library for showing images from network requests and also implementing caching in it.
- [Coroutines](https://developer.android.com/kotlin/coroutines) - Concurrency design pattern that can be used on Android to simplify code that executes asynchronously.

## Installation

To run the application locally, follow these steps:

1. Clone this repository:

   ```shell
   git clone https://github.com/iqbalShafiq/mobile-news-app.git

2. Open the folder/workspace by using Android Studio
3. After waiting the building of gradle, go to local.properties and set a new variable below:
   ```shell
   api_key=<your news api key from https://newsapi.org>

4. Run project.

## Usage And Screenshots
- There will be a splash screen that will be shown to the screen of the user for 2000ms (2 seconds).
<img src="https://i.ibb.co/wcJ4HBq/Splash-Screen.png" alt="Splash Screen" style="width:240px;"/>

- In the first screen that shows you a list of categories, you can choose one of the categories there. 
The categories are hard coded based on the API documentation, so there isn't any service that can get a list of categories. 
<img src="https://i.ibb.co/kMc2ndS/Category-List.png" alt="Category List Screen" style="width:240px;"/>

- After clicking on the category, the application will request the API to fetch a list of sources of news. 
Actually, this API won't support pagination for endless scrolling and search parameters. 
However, after trying to use this endpoint, the response of the API won't get a big amount of data, so I think it is the reason why the API does not support pagination. 
Also for the search feature, I created it manually using the filter function locally. I think it won't take a big amount of computation resources in the main thread, because I have filtered it in the background thread. 
So the application won't freeze when filtering the sources list. The search function will take 1500ms (1.5 seconds) after the user types the last letter to start the filtering method.
<img src="https://i.ibb.co/tP0CkgG/Source-List.png" alt="Source List Screen" style="width:240px;"/>

- After you click one of the sources, you will be moved to the article screen that shows you a list of articles based on the source that you have chosen before. 
Different from the sources service, the articles service supports pagination and search parameters. 
But the feature of the search function will take 1500ms (1.5 seconds) after the user types the last letter same as on the sources screen to load filtered articles from API.
<img src="https://i.ibb.co/vkDRN5G/Article-List.png" alt="Article List Screen" style="width:240px;"/>

- When you click on the articles, you will be moved to the article detail that shows you a Webview of the URL that is given from the articles API. Here you can also refresh the Webview if you think it is necessary.
<img src="https://i.ibb.co/kcWjYVM/Article-Detail.png" alt="Article Detail Screen" style="width:240px;"/>

## APK Download
Besides you can download the APK directly on this Github release, you can also download it via this [Google Drive link](https://drive.google.com/file/d/1-Y-pfcKCV8pYdek9Ut1LyyaJUpEqljvq/view?usp=sharing).
