$("#submit-form").submit ->
  postData = $("#submit-form").serialize()
  $.ajax
    url: "/submit"
    type: "post"
    dataType: "json"
    data: postData
    success: (data) ->
      console.log data
  return false