package com.abc.contracts.producer.domains;

import java.util.List;

public record PostResponse(Integer userId, List<Post> posts) {}
