package utils;

import main.Game;

import java.awt.*;

public class Constants {
	public static class ColorMapConstants {
		public static final String TILE = "tiles";
		public static final String ENTITY = "entity";
		public static final String OBJECT = "object";

		public static class DeathZone {
			public static final int DEATH_ZONE = 255;
		}

		public static class Player {
			public static final int PLAYER_SPAWN = 240;
		}
	}
	public static class EnemyConstants {
		public static final int CRABBY = 0;
		public static final Color CRABBY_COLOR = Color.decode("0x0b8000");

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;

		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFAULT = 32;

		public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
		public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);

		public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

		public static int GetSpriteAmount(int enemy_type, int enemy_state) {

			switch (enemy_type) {
				case CRABBY:
					switch (enemy_state) {
						case IDLE:
							return 9;
						case RUNNING:
							return 6;
						case ATTACK:
							return 7;
						case HIT:
							return 4;
						case DEAD:
							return 5;
					}
			}

			return 0;

		}

		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
				case CRABBY:
					return 10;
				default:
					return 1;
			}
		}

		public static int GetEnemyDmg(int enemy_type) {
			switch (enemy_type) {
				case CRABBY:
					return 15;
				default:
					return 0;
			}

		}

	}

	public static class Environment {
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
	}

	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 600;
			public static final int B_HEIGHT_DEFAULT = 200;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE) / 4;
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE) / 4;
		}

		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}

		public static class URMButtons {
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

		}

		public static class VolumeButtons {
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}

		public static class Background {
			public static final int BG_DEFAULT_SIZE = 128;
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int ATTACK = 4;
		public static final int HIT = 5;
		public static final int DEAD = 6;

		public static int GetSpriteAmount(int player_action) {
            return switch (player_action) {
                case DEAD -> 8;
                case RUNNING -> 6;
                case IDLE -> 5;
                case HIT -> 4;
                case JUMP, ATTACK -> 3;
                default -> 1;
            };
		}
	}

	public static class PlayerConstants2 {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int DOUBLE_JUMP = 3;
		public static final int CLIMB = 4;
		public static final int PUNCH = 5;
		public static final int RUNNING_PUNCH = 6;
		public static final int ATTACK_1 = 7;
		public static final int ATTACK_2 = 8;
		public static final int ATTACK_3 = 9;
		public static final int HURT = 10;
		public static final int DEATH = 11;

		public static String getFileName(int player_action) {
            return switch (player_action) {
                case RUNNING -> "run.png";
                case JUMP -> "jump.png";
                case DOUBLE_JUMP -> "doublejump.png";
                case CLIMB -> "climb.png";
                case PUNCH -> "punch.png";
                case RUNNING_PUNCH -> "run_attack.png";
                case ATTACK_1 -> "attack1.png";
                case ATTACK_2 -> "attack2.png";
                case ATTACK_3 -> "attack3.png";
                case HURT -> "hurt.png";
                case DEATH -> "death.png";
                default -> "idle.png";
            };
		}
	}


}