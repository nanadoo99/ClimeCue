// nx와 ny값을 기상청 api에 전송한다.
function sendNxNy(xy_result) {
    emptyValues();
    getWeather(xy_result);
    getPersonalCondition();
}

function getWeather(xy_result) {
    $.ajax({
        url: '/public/weather',
        type: 'GET',
        data: {nx : xy_result.x, ny : xy_result.y},
        dataType: 'json',
        success: function(data) {
            console.log('weatherData:', data);
            completeWeatherData(data);
            getOpenAi(data);
        },
        error: function(xhr) {
            var res = xhr.responseJSON;

            if (!res) {
                try {
                    res = JSON.parse(xhr.responseText);
                } catch(e) {
                    res = {};
                }
            }

            if (res && res.code === "VF_01") {
                invalidLocation();
            }

            alert("Code: " + res.code + "\nMessage: " + (res.message || "오류가 발생했습니다."));
        }
    });
}

function getOpenAi(weatherData) {
    $.ajax({
        url: '/public/openAi',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(weatherData),
        dataType: 'json',
        success: function(openAiResponse) {
            console.log('openAiResponse:', openAiResponse);
            completeOpenAIResponse(openAiResponse.message);
        },
        error: function(xhr) {
            var res = xhr.responseJSON;
            if (!res) {
                try {
                    res = JSON.parse(xhr.responseText);
                } catch(e) {
                    res = {};
                }
            }
            alert("Code: " + res.code + "\nMessage: " + (res.message || "오류가 발생했습니다."));
        }
    });
}

function getPersonalCondition() {
    $.ajax({
        url: '/public/personal',  // 엔드포인트 URL (필요에 따라 '/public/personal' 등으로 변경)
        type: 'GET',
        dataType: 'json',  // 응답을 JSON으로 자동 파싱
        success: function(data) {
            console.log('Personal condition data:', data);
            // 받아온 데이터를 화면에 업데이트하는 함수 호출
            completePersonalCondition(data);
        },
        error: function(xhr) {
            var res = xhr.responseJSON;
            if (!res) {
                try {
                    res = JSON.parse(xhr.responseText);
                } catch(e) {
                    res = {};
                }
            }
            alert("Code: " + (res.code || "Unknown") + "\nMessage: " + (res.message || "오류가 발생했습니다."));
        }
    });
}

function emptyValues() {
    $('#custom').hide();
    $('#weatherData').empty();
    $('#openAIResponse').empty();
    $('#age').val("");
    $('#bodyType').val("");
    $('#hobby').val("");
    $('#exercise').val("");
    $('#medical').val("");
}

function completeWeatherData(weatherData) {
    var categories = ["PTY", "REH", "RN1", "T1H", "WSD"];

    // weatherData.vilageFcstUltraSrtItems 배열이 존재하는지 체크합니다.
    var items = weatherData || [];

    categories.forEach(function(keyword) {
        // 해당 카테고리의 데이터를 필터링합니다.
        var filteredData = items.filter(function(item) {
            return item.category === keyword;
        });

        // 필터링된 데이터가 있다면 HTML 요소에 추가합니다.
        filteredData.forEach(function(data) {
            $('#weatherData').append('<p>' + data.categoryName + ': ' + data.obsrValueWithUnit + '</p>');
        });
    });
}

function completeOpenAIResponse(message) {
    $('#openAIResponse').append(message != null? message.content : "");
}

function completePersonalCondition(data) {
    var allNull = true;
    $.each(data, function(key, value) {
        if (value !== null) {
            allNull = false;
            return false;
        }
    });

    if (allNull) {
        $('#custom').show();
        return;
    }
    $('#age').val(data.age);
    $('#bodyType').val(data.bodyType);
    $('#hobby').val(data.hobby);
    $('#exercise').val(data.exercise);
    $('#medical').val(data.medical);
}