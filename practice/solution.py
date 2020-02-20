firstLine = input().split(' ')
secondLine = input().split(' ')
M = int(firstLine[0])
N = int(firstLine[1])
S = map(int, secondLine)
order = []

total = 0
while (True):
	S = list(filter(lambda s: s <= (M - total), S))
	sindex = S.index(max(S))
	total += S[sindex]
	del S[sindex]
	order.append(sindex)
	if (len(S) == 0):
		break

order.sort()
print(order)
