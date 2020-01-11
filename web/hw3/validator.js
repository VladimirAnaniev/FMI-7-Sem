const USERNAME_REGEX = /^[a-zA-Z0-9_]{3,10}$/;
const LOWERCASE_REGEX = /[a-z]/;
const UPPERCASE_REGEX = /[A-Z]/;
const DIGIT_REGEX = /[0-9]/;


window.onload = () => {
    const form = document.querySelector('#registrationForm');
    form.onsubmit = onSubmit;
};

function onSubmit(event) {
    event.preventDefault();
    const validationMessage = document.querySelector('#validationMessage');
    validationMessage.innerHTML = '';

    const form = event.target;
    let isValid = true;

    const username = form.querySelector('#username').value;
    if (!isUsenameValid(username)) {
        validationMessage.insertAdjacentHTML('beforeend', `<p>Username should be 3-10 characters long and contain only letters, numbers and _.</p>`)
        isValid = false;
    }

    const password = form.querySelector('#password').value;
    if(!isPasswordValid(password)) {
        validationMessage.insertAdjacentHTML('beforeend', `<p>Password should be at least 6 characters long and contain at least one uppercase, lowercase letter and a digit.</p>`)
        isValid = false;
    }

    const repeatPassword = form.querySelector('#repeatPassword').value;
    if(!doPasswordsMatch(password, repeatPassword)) {
        validationMessage.insertAdjacentHTML('beforeend', `<p>Passwords do not match</p>`)
        isValid = false;
    }

    if(isValid) {
        makePostRequest('register.php', {username, password});
    }
}

function isUsenameValid(username) {
    return USERNAME_REGEX.test(username);
}

function isPasswordValid(password) {
    return password.length >= 6
        && LOWERCASE_REGEX.test(password)
        && UPPERCASE_REGEX.test(password)
        && DIGIT_REGEX.test(password);
}

function doPasswordsMatch(password, repeatPassword) {
    return password === repeatPassword;
}

async function makePostRequest(url, data) {
    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    return await response.json();
}

