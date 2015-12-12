# Appengine Template

This is my project template for rapidly testing an idea. It is based on the following technologies
* Appengine for JAVA
* appengine-maven-plugin: building and deploying to AppEngine
* Restlet: exposing backend services with a RESTful interface
* AngularJS: Javascript MVC framework
* Bootstrap: simple but good looking styles
* npm: dependency manager based on node.js
* grunt: javascript task tool
* bower: javascript dependency management

## Getting Started
To get all dependencies installed type
```html
bower install
```

You can run the devserver by typing
```html
mvn appengine:devserver
```

To get livereload working run
```html
grunt server
```

## Fork this repo (inside squix78)
Create a new empty repo and clone it locally:
```html
git clone https://github.com/squix78/newcoolproject.git
```
Then enter the newcoolproject folder and
```html
git remote add upstream https://github.com/squix78/appengine-template.git
```
Pull a copy of the original repo:

```html
git fetch upstream
git merge upstream/master
```

Then push everything back to the new repo:
```html
git push origin master
```


## License

The MIT License

Copyright (c) 2014 Daniel Eichhorn

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
