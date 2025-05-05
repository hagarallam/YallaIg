package com.yallaIg.yallaIg_backend.constants;

public final class UrlConstants {

    // BASE URLS THAT ARE REUSABLE
    public static final String COURSES_BASE_URL = "/api/courses";
    public static final String INSTRUCTORS_BASE_URL = "/api/instructors";
    public static final String BLOGS_BASE_URL = "/api/blogs";
    public static final String RESOURCES_BASE_URL = "/api/resources";
    public static final String COMMUNITY_BASE_URL = "/api/community";



    // PUBLIC URLS FOR ALL HTTP METHOD
    public static final String[] PUBLIC_URLS = {
            "/auth/**",
            "/api/apply-to-teach",
            "/api/contact-us",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api-docs/**",
            "/v3/api-docs/**",
            "/webjars/**"
    };

    // PUBLIC URLS FOR GET HTTP METHOD
    public static final String[] PUBLIC_URLS_GET = {
            COURSES_BASE_URL,
            INSTRUCTORS_BASE_URL,
            BLOGS_BASE_URL,
            BLOGS_BASE_URL + "/category",
            RESOURCES_BASE_URL,
            RESOURCES_BASE_URL + "/category",
            RESOURCES_BASE_URL + "/categories",
            "/api/phone-codes",
            "/api/countries"
    };

    // ONLY ADMIN ALLOWED URLS FOR ALL HTTP METHOD
    public static final String[] ADMIN_URLS = {
            COMMUNITY_BASE_URL + "/comments/pending",
            COMMUNITY_BASE_URL + "/comments/{id}/approve",
            COMMUNITY_BASE_URL + "/comments/{id}/reject",
            COMMUNITY_BASE_URL + "/posts/pending",
            COMMUNITY_BASE_URL + "/posts/pending-comments",
            COMMUNITY_BASE_URL + "/posts/{id}/approve",
            COMMUNITY_BASE_URL + "/posts/{id}/reject",
            COURSES_BASE_URL + "/**",
            INSTRUCTORS_BASE_URL + "/**",
            BLOGS_BASE_URL + "/**",
            RESOURCES_BASE_URL + "/**",
            "/api/users/**",
            "/api/teachers-applications"
    };


    // ADMIN , STUDENT ALLOWED URLS FOR GET HTTP METHOD
    public static final String[] ADMIN_STUDENT_URLS_GET = {
            COURSES_BASE_URL + "/**",
            INSTRUCTORS_BASE_URL + "/**",
            BLOGS_BASE_URL + "/**",
            RESOURCES_BASE_URL + "/**",
    };


    // ONLY STUDENT ALLOWED URLS FOR ALL HTTP METHOD
    public static final String[] STUDENT_URLS = {
            "/api/dashboard/**",
            "/api/register/**",
            "/api/registered-courses",
            "/api/resources/register",
            "/api/orders"
    };

}
