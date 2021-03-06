function removeChoix(n) {
    $("#choix" + n).remove();
}

$(document).ready(function () {
    var count = 2;
    $("#isConclusion").change(function () {
        if ($(this).is(":checked")) {
            $('.choix').prop('readonly', true);
            $('#addChoix').prop('disabled', true);
            $('#isUnique').prop('disabled', true);
        } else {
            $('.choix').prop('readonly', false);
            $('#addChoix').prop('disabled', false);
            $('#isUnique').prop('disabled', false);
        }
    });

    $("#isUnique").change(function () {
        if ($(this).is(":checked")) {
            $('.extra-choice').remove();
            $('#addChoix').prop('disabled', true);
        } else {
            $('#addChoix').prop('disabled', false);
        }
    });

    $("#addChoix").click(function () {
        $("#choices").append('<div class="form-row align-items-center extra-choice" id="choix' + count + '">\n' +
            '                <div class="col-11 mb-3">\n' +
            '                    <textarea class="form-control choix" name="choices_text" rows="2" required></textarea>\n' +
            '                    <div class="invalid-tooltip">\n' +
            '                        Donne-moi un sens.\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="col-1 mb-3">\n' +
            '                    <button type="button" class="btn btn-light" onclick="removeChoix(' + count++ + ')"><i class="fas fa-trash-alt"></i></button>\n' +
            '                </div>\n' +
            '            </div>');
    });

});