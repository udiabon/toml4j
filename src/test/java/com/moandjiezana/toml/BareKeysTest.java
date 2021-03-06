package com.moandjiezana.toml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BareKeysTest {
  
  @Test
  public void should_ignore_spaces_around_key_segments() throws Exception {
    Toml toml = new Toml().parse("[ a . b   . c  ]  \n  key = \"a\"");
    
    assertEquals("a", toml.getString("a.b.c.key"));
  }

  @Test
  public void should_support_underscores_in_key_names() throws Exception {
    Toml toml = new Toml().parse("a_a = 1");

    assertEquals(1, toml.getLong("a_a").intValue());
  }

  @Test
  public void should_support_underscores_in_table_names() throws Exception {
    Toml toml = new Toml().parse("[group_a]\na = 1");

    assertEquals(1, toml.getLong("group_a.a").intValue());
  }

  @Test
  public void should_support_numbers_in_key_names() throws Exception {
    Toml toml = new Toml().parse("a1 = 1");

    assertEquals(1, toml.getLong("a1").intValue());
  }

  @Test
  public void should_support_numbers_in_table_names() throws Exception {
    Toml toml = new Toml().parse("[group1]\na = 1");

    assertEquals(1, toml.getLong("group1.a").intValue());
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_when_characters_outside_accept_range_are_used_in_table_name() throws Exception {
    new Toml().parse("[~]");
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_when_characters_outside_accept_range_are_used_in_table_array_name() throws Exception {
    new Toml().parse("[[~]]");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_when_dots_in_key_name() throws Exception {
    new Toml().parse("a.b = 1");
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_when_characters_outside_accept_range_are_used_in_key_name() throws Exception {
    new Toml().parse("~ = 1");
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_on_sharp_sign_in_table_name() throws Exception {
    new Toml().parse("[group#]\nkey=1");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_on_spaces_in_table_name() throws Exception {
    new Toml().parse("[valid  key]");
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_on_sharp_sign_in_table_array_name() throws Exception {
    new Toml().parse("[[group#]]\nkey=1");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_on_spaces_in_table_array_name() throws Exception {
    new Toml().parse("[[valid  key]]");
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_on_question_marks_in_key_name() throws Exception {
    new Toml().parse("key?=true");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_on_empty_table_name() {
    new Toml().parse("[]");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_on_nested_table_name_ending_with_empty_table_name() {
    new Toml().parse("[a.]");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_on_nested_table_name_containing_empty_table_name() {
    new Toml().parse("[a..b]");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_on_nested_table_name_starting_with_empty_table_name() {
    new Toml().parse("[.b]");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_on_nested_table_array_name_ending_with_empty_table_name() {
    new Toml().parse("[[a.]]");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_on_nested_table_array_name_containing_empty_table_name() {
    new Toml().parse("[[a..b]]");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_on_nested_table_array_name_starting_with_empty_table_name() {
    new Toml().parse("[[.b]]");
  }
}
