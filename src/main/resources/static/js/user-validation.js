"use strict";

function isValid(password) {
    console.log(/^[a-zA-Z0-9]*$/.test(password));
    return /^[a-zA-Z0-9]*$/.test(password);
}

$(document).ready(function () {
    let data = $('.data-check');
    let submit = $('#submit');
    data.on('keypress keyup keydown', function () {
        let password = $(this).val();
        submit.disabled = !isValid(password);
    });
});