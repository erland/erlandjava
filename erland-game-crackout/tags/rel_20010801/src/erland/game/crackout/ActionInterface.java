package erland.game.crackout;

interface ActionInterface
{
	void SafetyWall();
	void NewBall();
	void NewMonster(Monster m);
	void NewFeature(Feature f);
	void RemoveBall(Ball b);
	void RemoveMonster(Monster m);
	void RemoveFeature(Feature f);
	void LockBat();
	void IncreaseBallSpeed();
	void DecreaseBallSpeed();
	void IncreaseBatSpeed();
	void DecreaseBatSpeed();
	void ExtraLife();
	void DoubleBat();
	void NewMissile();
	void LargeBat();
	void SmallBat();
	void Explode(int x, int y, int sizeX, int sizeY);
}
