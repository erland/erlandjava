package erland.game.crackout;

interface CollisionRect
{
	int left();
	int right();
	int top();
	int bottom();
	void handleCollision(ActionInterface a);
}
