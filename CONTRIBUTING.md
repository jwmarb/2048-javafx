# Contributing to 2048 FX

Contributions are welcome! Whether you're an experienced developer or just starting out, there are many ways you can get involved. Please read through this document before submitting a pull request (PR) for your changes.

## Why contribute?

Contributing is a great way to learn, improve your skills and help others. It's also an opportunity to make a positive impact by contributing to open-source projects.

## How can I contribute?

### Reporting Bugs

- **Before reporting a bug**, make sure it hasn't already been reported by checking the existing issues.
- Provide as much information as possible:
  - Operating system and version you're using.
  - A clear description of the problem, including steps to reproduce.

### Suggesting Enhancements

We welcome suggestions for new features or enhancements. Please follow these guidelines when suggesting changes:

- Clearly explain what you would like changed and why it's important.
- Include any relevant links or references that support your suggestion.

### Adding / Refactoring Code

...even better! Your direct contribution to the project by making changes to code is highly appreciated! See below how you can do this:

## Development Setup

To contribute code changes:

1. **Fork the Repository**: Click "Fork" on the GitHub repository to create a copy in your own account.
2. **Clone Your Fork**: Clone your forked repository locally using Git: `git clone <your-fork-url>`.
3. **Branch Off of Master**: Create a new branch for your changes: `git checkout -b my-feature-branch`.

This project uses [gradle](https://gradle.org/) for building and managing dependencies. To run the application, simply run:

```sh
./gradlew run
```

## Submitting Changes

1. **Commit Your Changes**: Add and commit your changes using `git add` and `git commit`.
2. **Push to Your Fork**: Push your branch back to GitHub with `git push origin my-feature-branch`.
3. **Create a Pull Request (PR)**: Go to the original repository, click "New pull request", select your fork as base, then compare across branches.
4. **Describe Changes**: Provide clear descriptions for each commit and overall PR.

## Code Style

Please adhere to the following guidelines:

- Use standard Java naming conventions.
- Keep line lengths under 120 characters.
- Follow consistent indentation (use spaces over tabs).
- Ensure proper formatting using an IDE or tool like `checkstyle`.

### Testing Your Changes

Before submitting a pull request, ensure your changes don't break existing functionality. You can run the game locally and check if everything works as expected.

If you're adding new features, please include unit tests where applicable to help verify correctness.

All test cases are located under `app/src/test/java/org/csc335`.
