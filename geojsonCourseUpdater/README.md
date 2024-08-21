# Golf Course Updater using GPS and GeoJSON Polygons
## Project Overview
This project was completed by a 5-person team of Clemson students collaborating with top sports tech company and official PGA partner, Arccos. Arccos' primary business efforts revolve around their mobile golf caddie app that tracks shot and play data, offering real-time stats and data summaries to help golfers shoot lower scores. My team and I were tasked with brainstorming, designing, and implementing a solution to streamline the correction of errors in their mapping database of more than 40,000 golf courses worldwide. We chose to develop a support ticket flow that could be used by Arccos users to submit a GeoJSON outline of a golf feature (tee box, green, bunker, etc) that is inaccurately marked in Arccos’ system. Once integrated with Arccos’ app, our efforts will save Arccos hundreds of hours of time spent manually remapping courses using on text-based support tickets.

## Project Details
- Written in Typescript
- Developed and tested using React Native's Expo
- Utilized Agile framework (weekly sprints, deliverable presentations, and standups with lead Arccos engineers)


## Installing

Before you start developing this app, you need to set up NPM and Node. Follow [these instructions](https://stackoverflow.com/a/19584407/17186475) to check if Node and NPM are installed and, if not, install the latest Node version. After updating Node, similarly update NPM using [these instructions](https://docs.npmjs.com/try-the-latest-stable-version-of-npm).

To start developing this app, firstly clone this repository into your current directory:

```console
git clone https://github.com/marksheldon2/marksheldonrepo.git
```

Then simply install Expo and its dependencies, listed in `package.json`:

```console
npm install
```

## Developing

The Node framework used is Expo, which is derived from React Native and uses React as its development framework. Expo requires an additional app, called "Expo Go", to develop iOS apps with, available on both the App Store and Google Play Store. Local development through a web browser is also possible if forced to work offline.

If any error occurs with the following steps, make sure to read the message and do whatever it asks you to; similarly, if any command you run asks to install a dependency, allow it to.

Make sure you create an account with Expo through either the app or their website. Then, log in to the app using your credentials, and log into the Expo CLI as well by running `npx expo login` in the root directory of the cloned repository and entering your credentials when prompted.

After logging in, to start a development server, run `npx expo start --tunnel` (or `npx expo start -c --tunnel`, if you're running the dev server after a while). This should open up a dev server; enter `w` to serve to a webpage, or open the Expo Go app to develop on a mobile platform. This project should show up on the main screen on the app; press it and it will load.

Fair warning: starting the dev server may take a while; if the Expo Go app is displaying an error message (something along the lines of "Could not connect to development server") give it some time. Changes made should be automatically updated, whether you are using the web server or the app.
