<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<!-- Credit to othneildrew for the readme template, check out link below to learn more -->
<!-- See: https://github.com/othneildrew/Best-README-Template/ -->



<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/paullert/project_1_s1_01">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Crypto Tracker</h3>

  <p align="center">
    Simple Android app that allows users to save crypto values at points in time to compare to current market price.
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This is a simple android app that uses composables to create a simple 

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![Android Studio][AndroidStudio]][AndroidStudio-url]
* [![Jetpack Compose][Compose]][Compose-url]
* [![Android Room 3.0][Room3]][Room3-url]
* [![SQLite][SQLite]][SQLite-url]
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites
* Android Studio (Quail 4)
  Click the badge above to visit the home page to learn more.

### Installation

1. Get a Demo API Key at [https://www.coingecko.com/en/api](https://www.coingecko.com/en/api)
2. Clone the repo
   ```sh
   git clone https://github.com/paullert/project_1_s1_01.git
   ```
3. Build Project Using Gradle
   ```sh
   ./gradlew build
   ```
4. Enter your API Key in `secrets.properties`
   ```properties
   COINGECKO_API_KEY = 'ENTER YOUR API KEY';
   ```
5. Change git remote url to avoid accidental pushes to base project
   ```sh
   git remote set-url origin paullert/project_1_s1_01
   git remote -v # confirm the changes
   ```
6. Run the App via android emulator or physical phone.
    This has not been tested on a real device, its recommended to emulate it (Offical Emulation Information)


<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Top contributors:

<a href="https://github.com/paullert/project_1_s1_01/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=paullert/project_1_s1_01" alt="contrib.rocks image" />
</a>

Project Link: [https://github.com/paullert/project_1_s1_01](https://github.com/paullert/project_1_s1_01)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [Best-README-Template](https://github.com/othneildrew/Best-README-Template/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/paullert/project_1_s1_01.svg?style=for-the-badge
[contributors-url]: https://github.com/paullert/project_1_s1_01/graphs/contributors
[Compose-url]: https://developer.android.com/compose
[Room3-url]: https://developer.android.com/jetpack/androidx/releases/room3
[AndroidStudio-url]:https://developer.android.com/studio
[SQLite-url]: https://www.sqlite.org/

<!-- Shields.io badges. You can a comprehensive list with many more badges at: https://github.com/inttter/md-badges -->
[AndroidStudio]: https://img.shields.io/badge/Android%20Studio-000000?style=for-the-badge&logo=androidstudio&logoColor=34a853
[Compose]: https://img.shields.io/badge/Compose-20232A?style=for-the-badge&logo=jetpackcompose&logoColor=34a853
[Room3]: https://img.shields.io/badge/Room%203-000000?style=for-the-badge&logo=android&logoColor=34a853
[SQLite]: https://img.shields.io/badge/sql-ffffff?style=for-the-badge&logo=sqlite&logoColor=044a64