"""Observer pattern demo."""
from .iphone_launch_notifier import IPhoneLaunchNotifier
from .amazon_user import AmazonUser


def main():
    product_launch_notifier = IPhoneLaunchNotifier("iphone 18")

    user1 = AmazonUser("Mike")
    user2 = AmazonUser("Math")
    user3 = AmazonUser("Rocky")

    product_launch_notifier.subscribe(user1)
    product_launch_notifier.subscribe(user2)
    product_launch_notifier.subscribe(user3)

    product_launch_notifier.unsubscribe(user2)

    product_launch_notifier.notify_customers()


if __name__ == "__main__":
    main()
