// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: snakes.proto
package dto;

public interface GamePlayerOrBuilder extends
    // @@protoc_insertion_point(interface_extends:snakes.GamePlayer)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>required string name = 1;</code>
   */
  boolean hasName();
  /**
   * <code>required string name = 1;</code>
   */
  java.lang.String getName();
  /**
   * <code>required string name = 1;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>required int32 id = 2;</code>
   */
  boolean hasId();
  /**
   * <code>required int32 id = 2;</code>
   */
  int getId();

  /**
   * <code>required string ip_address = 3;</code>
   */
  boolean hasIpAddress();
  /**
   * <code>required string ip_address = 3;</code>
   */
  java.lang.String getIpAddress();
  /**
   * <code>required string ip_address = 3;</code>
   */
  com.google.protobuf.ByteString
      getIpAddressBytes();

  /**
   * <code>required int32 port = 4;</code>
   */
  boolean hasPort();
  /**
   * <code>required int32 port = 4;</code>
   */
  int getPort();

  /**
   * <code>required .snakes.NodeRole role = 5;</code>
   */
  boolean hasRole();
  /**
   * <code>required .snakes.NodeRole role = 5;</code>
   */
  NodeRole getRole();

  /**
   * <code>optional .snakes.PlayerType type = 6 [default = HUMAN];</code>
   */
  boolean hasType();
  /**
   * <code>optional .snakes.PlayerType type = 6 [default = HUMAN];</code>
   */
  PlayerType getType();

  /**
   * <code>required int32 score = 7;</code>
   */
  boolean hasScore();
  /**
   * <code>required int32 score = 7;</code>
   */
  int getScore();
}
