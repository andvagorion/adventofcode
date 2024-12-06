from aoc import aoc
from functools import reduce

lines = aoc.read_lines('input/04.txt')

bingo_numbers = [int(i) for i in lines[0].split(',')]

boards = []

i = 1
while i < len(lines):
    make_board = lambda l: [int(i) for i in l.split()]
    board = [make_board(line) for line in lines[i+1:i+6]]
    boards.append(board)
    i += 6

def count_hor(board, y):
    return reduce(lambda a, b: a + b, board[y])

def count_vert(board, x):
    nums = [board[y][x] for y in range(5)]
    return reduce(lambda a, b: a + b, nums)

def has_bingo(board):
    for y in range(5):
        if count_hor(board, y) == -5: return True
    for x in range(5):
        if count_vert(board, x) == -5: return True

def any_winner(boards):
    for board in boards:
        if has_bingo(board): return True
    return False

def strike(board, num):
    for y in range(5):
        for x in range(5):
            if board[y][x] == num: board[y][x] = -1

bingo_round = 0
while not any_winner(boards):
    next_num = bingo_numbers[bingo_round]
    for board in boards:
        strike(board, next_num)
    bingo_round += 1

winner = None
winners = []

for i in range(len(boards)):
    board = boards[i]
    if has_bingo(board):
        winners.append(i)
        winner = board
        break

def score(board):
    sum = 0
    fixed = [[0 if num == -1 else num for num in line] for line in board]
    for line in fixed:
        sum += reduce(lambda a,b: a + b, line)
    return sum

print(next_num * score(winner))

def all_winners(boards):
    for i in range(len(boards)):
        board = boards[i]
        if i not in winners and has_bingo(board):
            winners.append(i)
    return len(winners) == len(boards)

while not all_winners(boards):
    next_num = bingo_numbers[bingo_round]
    for board in boards:
        strike(board, next_num)
    bingo_round += 1

last_winner = winners[-1]

sum = next_num * score(boards[last_winner])
print(sum)