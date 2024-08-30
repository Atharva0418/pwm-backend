package com.atharvadholakia.password_manager.validation.group;

import jakarta.validation.GroupSequence;

@GroupSequence({FirstGroup.class, SecondGroup.class, ThirdGroup.class})
public interface ValidationSequence {}
