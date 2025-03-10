import asyncio
from aiogram import Bot, Dispatcher, F

from app.handlers import router



async def main():
    bot = Bot(token='7409844758:AAHS44CRZTB62VNIF02T_5aqqlJ1axwghQ0')
    dp = Dispatcher()
    dp.include_router(router)
    await dp.start_polling(bot)

if __name__ == '__main__':
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        print('Bot jest wyłączony')

