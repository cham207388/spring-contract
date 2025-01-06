package com.abc.contracts.consumer.domains;

import java.util.List;

public record PostResponse(Integer userId, List<Post> posts) {}
