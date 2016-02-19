gradle-swig
===========

A simple `swig` Java target wrapper.

This plugin will define a new task of type `SwigTask`, which accepts the following options:

| Property | Required? | Description | Example |
| --- | --- | --- | --- |
| `verbose` | no | Verbose output | `true/false` |
| `enableCpp` | no | Enable/disable c++ parsing (default `false`) | `true/false` |
| `symbols` | no | Additional preprocessor symbols. | `['SWIGWORDSIZE64', 'FOO']` |
| `module` | yes | Target module name | `'groundhog'` |
| `packageName` | yes | Target Java package for generated Java sources | `net.tribe7.foo` |
| `source` | yes | Source header/interface file | `new File('./src/test/resources/foo.h')` |
| `includePaths` | yes | Additional header include paths | `[new File('./src/test/resources/time')]` |
| `javaSourcesPath` | yes | Target location for generated Java sources | `new File('./build/resources/test')` |
| `wrapperTargetFile` | yes | Target file name/location for generated c/c++ JNI wrapper | `new File('./build/resources/test/foo_wrap.cpp')` |

## Current caveats.

- Right now, the plugin only wraps the command line `swig` options pertaining to the
Java target.

- `swig` must be present and accessible via your system `PATH`. Otherwise the plugin will fail.

If there's enough interest, I may enhance it to generate interfaces
for the rest of the supported languages.

[![npm](https://img.shields.io/npm/l/express.svg)]()
[![Gemnasium](https://img.shields.io/gemnasium/mathiasbynens/he.svg)]()
