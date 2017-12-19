import oss2
from itertools import islice
auth = oss2.Auth('3Yvy23QypBtVsJhz', 'zAXajwCAO51Qw2g8xg4WL5asmH31nr')
bucket = oss2.Bucket(auth, 'oss-cn-hangzhou.aliyuncs.com', 'ctdn')
for i,b in enumerate(islice(oss2.ObjectIterator(bucket), 100)):
    print(i,b.key)