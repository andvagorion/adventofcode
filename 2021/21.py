SAMPLE = [4, 8]
DATA = [7, 9]

def game(positions, dice_rolls):
    player = 0
    pos = positions
    score = [0, 0]

    while score[0] < 1000 and score[1] < 1000:
        dice = (dice_rolls.pop(0),dice_rolls.pop(0),dice_rolls.pop(0))
        spaces = sum(dice)
        pos[player] = pos[player] + spaces
        pos[player] %= 10
        score[player] += 10 if pos[player] == 0 else pos[player]
        player = 0 if player == 1 else 1
    
    return (dice_rolls[0] - 1) * min(score)

part1 = game(SAMPLE, [i for i in range(1, 5000)])
print(part1)