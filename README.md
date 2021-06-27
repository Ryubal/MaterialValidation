# MaterialValidation ✅
Android library to help programmers validate Material Design forms easier

## Table of contents
1. [Requirements](#requirements)
2. [Installation](#installation)
3. [Sample code](#sample-code)
4. [Types of validation](#types-of-validation)
	1. [Regex or pattern validations](#regex-or-pattern-validations)
	2. [Range validations](#range-validations)
	3. [Simple validations](#simple-validations)
	4. [Custom validations](#custom-validations)
	5. [Custom manual validations](#custom-manual-validations)
5. [Several validations on the same input](#several-validations-on-the-same-input)
6. [Retrieving failed inputs](#retrieving-failed-inputs)
7. [Future plans](#future-plans)
8. [Contributing](#contributing)

## Requirements
1. An Android Studio project (Java)
2. Material Design components (this library was developed with v1.3.0)

## Installation
To start using MaterialValidation, you need to import this dependency into your project. If you're using Gradle, add the following lines to your `build.gradle` (module) and sync your project:
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

```gradle
dependencies {
	implementation 'com.github.Ryubal.MaterialValidation:v1.0.0'
}
```

## Usage
Using this library is pretty straight forward. Just follow these steps:
1. Create a new instance of the library
2. Add your validation rules
3. Run the validations when you're ready

> **Important**: You must implement Material Design components according to their documentation. In order for this library to work, you must have a `TextInputLayout`, and inside, a `TextInputEditText` or `MaterialAutoCompleteTextView`. Then, whenever you add a new validation rule, you must pass a reference to the `TextInputLayout` as a first argument (see below)

To see more examples about the usage and integration of MaterialValidation, clone this repository and run the app on a device or emulator.

## Sample code
Here's a pretty basic snippet:
```java
// 1. Create new instance
MaterialValidation validator = new MaterialValidation();

// 2. Add validation rules
validator.add(binding.textInputUsername, Regex.NON_EMPTY, "Please enter your username");
validator.add(binding.textInputPassword, Regex.NON_EMPTY, "Please enter your password");

// 3. Run the validations
if(validator.validate())
	Toast.makeText(this, "Fields are valid!", Toast.LENGTH_SHORT).show();
```

> Please note that the first argument we passed to the `add` method is a reference to a `TextInputLayout`

> The last argument passed to the `add` method is an error to be shown if the field is invalid upon validation

## Types of validation
MaterialValidation comes with a wide variety of validation methods: regex, ranges, length, custom validations, etc. Below you'll be able to find all methods available.

### Regex or pattern validations
There are 3 ways to use regular expressions: raw string, a pre-compiled pattern, or using a built-in regex. As of now, MaterialValidation comes with 4 built-in regular expressions.
```java
// Using a raw string with a regular expression
validator.add(binding.yourTextInputLayout, "^[a-zA-Z]+$", "Error message here");

// Using a pre-compiled pattern
Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
validator.add(binding.yourTextInputLayout, pattern, "Error message here");

// Using built-in regular expressions (4 available)
// This will match any letter, at least once
validator.add(binding.yourTextInputLayout, Regex.LETTERS, "Error message here");

// This will match any number, at least once
validator.add(binding.yourTextInputLayout, Regex.NUMBERS, "Error message here");

// This will match any letter or number, at least once
validator.add(binding.yourTextInputLayout, Regex.LETTERS_NUMBERS, "Error message here");

// This will match any character, at least once
validator.add(binding.yourTextInputLayout, Regex.NON_EMPTY, "Error message here");
```

### Range validations
You can validate an input to be numeric, within a specified range:
```java
// Input must be a number between 3 and 10 (both inclusive)
validator.add(binding.yourTextInputLayout, new Range(3, 10), "Error message here");
```

### Simple validations
There are simple validations that can be expressed with a regex, but are built into the library for ease of use:
```java
// Input must be at least 3 characters long
validator.add(binding.yourTextInputLayout, Simple.MIN_LENGTH, 3, "Error message here");

// Input can't be more than 5 characters long
validator.add(binding.yourTextInputLayout, Simple.MAX_LENGTH, 5, "Error message here");
```

### Custom validations
If you'd like to perform your own custom validation, you can do so by implementing the `CustomValidation` interface:
```java
// You can do so the traditional way...
validator.add(binding.passwordConfirmation, new CustomValidation() {
	@Override
	public boolean validate(String input) {
		String password = binding.password.getEditText().getText().toString();

		if(password.equals(input))
			return true;		// Return true because it's valid
		return false;			// Return false because it's invalid
	}
}, "Both passwords must match");

// ... or using Java 8's lambda expressions
validator.add(binding.passwordConfirmation, input -> {
	String password = binding.password.getEditText().getText().toString();

	if(password.equals(input))
		return true;
	return false;
}, "Both passwords must match");
```

### Custom manual validations
There might be the case when you want to use components that are not supported by MaterialValidation. When that's the case, you need a way to define your own validation rules and error handler, and pass those into the validation flow. You can do so by implementing the `CustomManualValidation` interface:
```java
// We'll be validating a checkbox. It has to be selected. Traditional way...
validator.add(new CustomManualValidation() {
	@Override
	public boolean validate() {
		boolean isChecked = binding.myCheckbox.isChecked();
		if(!isChecked)
			Toast.makeText(this, "You must accept the terms and conditions", Toast.LENGTH_SHORT).show();

		// Return result of the validation
		return isChecked;
	}
});

// ... or using lambda expressions
validator.add(() -> {
	boolean isChecked = binding.myCheckbox.isChecked();
	if(!isChecked)
		Toast.makeText(this, "You must accept the terms and conditions", Toast.LENGTH_SHORT).show();

	// Return result of the validation
	return isChecked;
});
```

## Several validations on the same input
Yes! You can run more than one validation on the same input:
```java
validator.add(binding.password, Regex.NON_EMPTY, "Please enter your new password");
validator.add(binding.password, Simple.MIN_LENGTH, 3, "Password must be at least 3 characters long");
validator.add(binding.password, Simple.MAX_LENGTH, 15, "Password can't be more than 15 characters long");
```

Validations will run in order. If the first one fails, no further validations will be applied on the same field until the validation runs again.

## Retrieving failed inputs
There are cases when you might need to know which fields failed the validation. Everytime you run the `validate()` process, MaterialValidation will update a list of the `TextInputLayout` elements that failed. To get that list, call `getInvalidFields()`:
```java
// After initializing the object and adding validations...
if(!validator.validate()) {
	List<TextInputLayout> invalidFields = validator.getInvalidFields();

	Toast.makeText(this, invalidFields.size() + " fields were invalid", Toast.LENGTH_SHORT).show();
}
```

To see an implementation of this approach, take a look at the `BasicValidationActivity`.

## Future plans
Right now MaterialValidation is fully stable, though new features are pending to be added, such as:
- Validations without view binding (using the usual methods)
- Keeping track of invalid custom validations
- Migration to Kotlin
- Adding more regular expresions and simple validations

## Contributing
Contributions are very welcome! Here's how:
1. [Fork this repo](https://github.com/ryubal/MaterialValidation/fork) 🍴
2. Clone your fork `git clone https://github.com/YOUR-USERNAME/materialvalidation.git`
3. Create your feature branch `git checkout -b MY-FEATURE-NAME`
4. Commit your changes `git commit -m 'MY FEATURE DESCRIPTION'`
5. Push to the branch `git push origin MY-FEATURE-NAME`
6. Submit a pull request! 🔌