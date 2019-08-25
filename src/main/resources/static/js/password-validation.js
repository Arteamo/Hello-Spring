"use strict";

function scorePassword(pass) {
    var score = 0;
    if (!pass)
        return score;

    var letters = {};
    for (var i = 0; i < pass.length; i++) {
        letters[pass[i]] = (letters[pass[i]] || 0) + 1;
        score += 5.0 / letters[pass[i]];
    }

    var variations = {
        digits: /\d/.test(pass),
        lower: /[a-z]/.test(pass),
        upper: /[A-Z]/.test(pass),
        nonWords: /\W/.test(pass)
    };

    var variationCount = 0;
    for (var check in variations) {
        variationCount += (variations[check] === true) ? 1 : 0;
    }
    score += (variationCount - 1) * 10;

    return parseInt(score);
}

function checkPassStrength(pass) {
    var score = scorePassword(pass);
    var $pass = $("#password");

    if (score >= 60) {
        $pass.addClass("is-valid");
    } else {
        $pass.removeClass("is-valid");
    }
}

$(document).ready(function () {
    var $pass = $('#password');
    $pass.on('keypress keyup keydown', function () {
        var pass = $(this).val();
        $("#password-check").text(checkPassStrength(pass));
    });
});