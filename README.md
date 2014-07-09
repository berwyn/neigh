neigh
=====

The sound horses make

Make sure to create a local.gradle in the top-level folder that looks something like this:

```gradle
retrolambda {
    jdk 'path/to/jdk8'
    oldJdk 'path/to/jdk7'
    javaVersion JavaVersion.VERSION_1_7
}
```